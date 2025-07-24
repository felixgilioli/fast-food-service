package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import java.util.UUID

class ConfirmarPedidoCozinhaUseCase(
    private val pedidoGateway: PedidoGateway
) {

    fun execute(pedidoId: UUID) = pedidoGateway.findById(pedidoId)
        ?.let { pedidoGateway.save(it.copy(status = StatusPedido.EM_PREPARACAO)) }
        ?: throw IllegalArgumentException("Pedido não encontrado")
}