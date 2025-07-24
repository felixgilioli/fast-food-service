package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.application.ports.driver.PedidoUseCase
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import java.util.*

class PedidoUseCaseImpl(
    private val pedidoGateway: PedidoGateway
) : PedidoUseCase {

    override fun pedidoPronto(pedidoId: UUID) = pedidoGateway.findById(pedidoId)
        ?.let { pedidoGateway.save(it.copy(status = StatusPedido.PRONTO)) }
        ?: throw IllegalArgumentException("Pedido não encontrado")

    override fun retirarPedido(pedidoId: UUID) = pedidoGateway.findById(pedidoId)
        ?.let { pedidoGateway.save(it.copy(status = StatusPedido.FINALIZADO)) }
        ?: throw IllegalArgumentException("Pedido não encontrado")
}