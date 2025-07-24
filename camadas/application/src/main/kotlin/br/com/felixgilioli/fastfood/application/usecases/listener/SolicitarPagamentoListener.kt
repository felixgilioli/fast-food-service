package br.com.felixgilioli.fastfood.application.usecases.listener

import br.com.felixgilioli.fastfood.application.events.EventListener
import br.com.felixgilioli.fastfood.application.events.EventPublisher
import br.com.felixgilioli.fastfood.application.events.LinkPagamentoCriadoEvent
import br.com.felixgilioli.fastfood.application.events.PedidoConfirmadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway

class SolicitarPagamentoListener(
    private val pagamentoGateway: PagamentoGateway,
    private val eventPublisher: EventPublisher
) : EventListener<PedidoConfirmadoEvent> {

    override fun onEvent(event: PedidoConfirmadoEvent) {
        if (event.pedido.total == null) {
            throw IllegalArgumentException("O pedido não possui valor total definido.")
        }

        LinkPagamentoCriadoEvent(event.pedido, pagamentoGateway.gerarLink(event.pedido.total!!))
            .let(eventPublisher::publish)
    }
}