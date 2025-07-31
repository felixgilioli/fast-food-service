package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import java.util.*

class DefinirPedidoProntoUseCase(
    private val pedidoGateway: PedidoGateway
) {

    fun execute(pedidoId: UUID) = pedidoGateway.findById(pedidoId)
        ?.let { pedidoGateway.save(it.copy(status = StatusPedido.PRONTO)) }
        ?: throw IllegalArgumentException("Pedido não encontrado")
}