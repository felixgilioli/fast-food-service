package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.events.EventListener
import br.com.felixgilioli.fastfood.core.events.PedidoConfirmadoEvent

class SolicitarPagamentoUseCaseImpl : EventListener<PedidoConfirmadoEvent> {

    override fun onEvent(event: PedidoConfirmadoEvent) {
        println("solicitando pagamento para o pedido ${event.pedido.id}")
    }
}