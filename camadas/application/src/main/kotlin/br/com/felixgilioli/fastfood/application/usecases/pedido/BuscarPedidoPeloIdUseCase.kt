package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import java.util.UUID

class BuscarPedidoPeloIdUseCase(
    private val pedidoGateway: PedidoGateway
) {

    fun execute(pedidoId: UUID) = pedidoGateway.findById(pedidoId)
}