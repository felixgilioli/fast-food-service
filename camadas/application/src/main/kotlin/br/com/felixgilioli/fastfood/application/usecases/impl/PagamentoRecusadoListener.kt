package br.com.felixgilioli.fastfood.application.usecases.impl.listener

import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.application.events.EventListener
import br.com.felixgilioli.fastfood.application.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.application.ports.driven.PedidoRepository

class PagamentoRecusadoListener(private val pedidoRepository: PedidoRepository) :
    EventListener<PagamentoRecusadoEvent> {

    override fun onEvent(event: PagamentoRecusadoEvent) {
        event.pedido.copy(status = StatusPedido.PAGAMENTO_RECUSADO)
            .let(pedidoRepository::save)
    }
}