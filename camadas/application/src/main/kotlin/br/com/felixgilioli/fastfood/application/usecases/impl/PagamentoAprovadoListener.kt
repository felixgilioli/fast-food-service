package br.com.felixgilioli.fastfood.application.usecases.impl.listener

import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.application.events.EventListener
import br.com.felixgilioli.fastfood.application.events.PagamentoAprovadoEvent
import br.com.felixgilioli.fastfood.application.ports.driven.PedidoRepository

class PagamentoAprovadoListener(private val pedidoRepository: PedidoRepository) :
    EventListener<PagamentoAprovadoEvent> {

    override fun onEvent(event: PagamentoAprovadoEvent) {
        event.pedido.copy(status = StatusPedido.PAGAMENTO_APROVADO)
            .let(pedidoRepository::save)
    }
}