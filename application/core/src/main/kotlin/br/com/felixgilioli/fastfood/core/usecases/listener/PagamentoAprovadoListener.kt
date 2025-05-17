package br.com.felixgilioli.fastfood.core.usecases.listener

import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import br.com.felixgilioli.fastfood.core.events.EventListener
import br.com.felixgilioli.fastfood.core.events.PagamentoAprovadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.PedidoRepository

class PagamentoAprovadoListener(private val pedidoRepository: PedidoRepository) :
    EventListener<PagamentoAprovadoEvent> {

    override fun onEvent(event: PagamentoAprovadoEvent) {
        event.pedido.copy(status = StatusPedido.PAGAMENTO_APROVADO)
            .let(pedidoRepository::save)
    }
}