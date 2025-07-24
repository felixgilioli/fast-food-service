package br.com.felixgilioli.fastfood.application.usecases.listener

import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.application.events.EventListener
import br.com.felixgilioli.fastfood.application.events.PagamentoAprovadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway

class PagamentoAprovadoListener(private val pedidoGateway: PedidoGateway) :
    EventListener<PagamentoAprovadoEvent> {

    override fun onEvent(event: PagamentoAprovadoEvent) {
        event.pedido.copy(status = StatusPedido.PAGAMENTO_APROVADO)
            .let(pedidoGateway::save)
    }
}