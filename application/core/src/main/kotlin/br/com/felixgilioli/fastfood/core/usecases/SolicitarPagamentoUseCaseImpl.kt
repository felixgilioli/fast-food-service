package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.events.EventListener
import br.com.felixgilioli.fastfood.core.events.PedidoConfirmadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.GeradorLinkPagamento

class SolicitarPagamentoUseCaseImpl(private val geradorLinkPagamento: GeradorLinkPagamento) : EventListener<PedidoConfirmadoEvent> {

    override fun onEvent(event: PedidoConfirmadoEvent) {
        if (event.pedido.total == null) {
            throw IllegalArgumentException("O pedido não possui valor total definido.")
        }

        println("solicitando pagamento para o pedido ${event.pedido.id}")
        val linkPagamento = geradorLinkPagamento.gerarLink(event.pedido.total)

        println("link de pagamento gerado: $linkPagamento")
    }
}