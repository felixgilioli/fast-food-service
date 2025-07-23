package br.com.felixgilioli.fastfood.application.usecases.listener

import br.com.felixgilioli.fastfood.domain.entities.Pagamento
import br.com.felixgilioli.fastfood.domain.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.application.events.EventListener
import br.com.felixgilioli.fastfood.application.events.LinkPagamentoCriadoEvent
import br.com.felixgilioli.fastfood.application.ports.driven.PagamentoRepository
import br.com.felixgilioli.fastfood.application.ports.driven.PedidoRepository

class AtualizarPedidoLinkPagamentoGeradoListener(
    private val pagamentoRepository: PagamentoRepository,
    private val pedidoRepository: PedidoRepository
) : EventListener<LinkPagamentoCriadoEvent> {

    override fun onEvent(event: LinkPagamentoCriadoEvent) {
        Pagamento(
            pedido = event.pedido,
            valor = event.pedido.total!!,
            status = PagamentoStatus.LINK_PAGAMENTO_GERADO,
            link = event.linkPagamento
        ).let(pagamentoRepository::insert)

        event.pedido.copy(status = StatusPedido.PAGAMENTO_SOLICITADO)
            .let(pedidoRepository::save)

    }
}