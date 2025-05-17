package br.com.felixgilioli.fastfood.core.usecases.listener

import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import br.com.felixgilioli.fastfood.core.events.EventListener
import br.com.felixgilioli.fastfood.core.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.PedidoRepository

class PagamentoRecusadoListener(private val pedidoRepository: PedidoRepository) :
    EventListener<PagamentoRecusadoEvent> {

    override fun onEvent(event: PagamentoRecusadoEvent) {
        event.pedido.copy(status = StatusPedido.PAGAMENTO_RECUSADO)
            .let(pedidoRepository::save)
    }
}