package br.com.felixgilioli.fastfood.core.usecases.listener

import br.com.felixgilioli.fastfood.core.events.EventListener
import br.com.felixgilioli.fastfood.core.events.LinkPagamentoCriadoEvent
import br.com.felixgilioli.fastfood.core.events.PedidoConfirmadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.core.ports.driven.GeradorLinkPagamento

class SolicitarPagamentoListener(
    private val geradorLinkPagamento: GeradorLinkPagamento,
    private val eventPublisher: EventPublisher
) : EventListener<PedidoConfirmadoEvent> {

    override fun onEvent(event: PedidoConfirmadoEvent) {
        if (event.pedido.total == null) {
            throw IllegalArgumentException("O pedido não possui valor total definido.")
        }

        LinkPagamentoCriadoEvent(event.pedido, geradorLinkPagamento.gerarLink(event.pedido.total))
            .let(eventPublisher::publish)
    }
}