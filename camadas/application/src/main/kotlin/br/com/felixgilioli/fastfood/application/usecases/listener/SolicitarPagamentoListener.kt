package br.com.felixgilioli.fastfood.application.usecases.listener

import br.com.felixgilioli.fastfood.application.events.EventListener
import br.com.felixgilioli.fastfood.application.events.LinkPagamentoCriadoEvent
import br.com.felixgilioli.fastfood.application.events.PedidoConfirmadoEvent
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.application.ports.driven.GeradorLinkPagamento

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