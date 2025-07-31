package br.com.felixgilioli.fastfood.application.usecases.listener

import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.application.events.EventListener
import br.com.felixgilioli.fastfood.application.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway

class PagamentoRecusadoListener(private val pedidoGateway: PedidoGateway) :
    EventListener<PagamentoRecusadoEvent> {

    override fun onEvent(event: PagamentoRecusadoEvent) {
        event.pedido.copy(status = StatusPedido.PAGAMENTO_RECUSADO)
            .let(pedidoGateway::save)
    }
}