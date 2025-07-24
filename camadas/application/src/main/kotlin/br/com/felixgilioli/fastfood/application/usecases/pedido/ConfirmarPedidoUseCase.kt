package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.application.commands.ConfirmarPedidoCommand
import br.com.felixgilioli.fastfood.application.events.PedidoConfirmadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.PedidoItem
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import java.math.BigDecimal

class ConfirmarPedidoUseCase(
    private val pedidoGateway: PedidoGateway,
    private val produtoGateway: ProdutoGateway,
    private val eventPublisher: EventPublisher
) {

    fun execute(command: ConfirmarPedidoCommand): Pedido {
        val pedido = pedidoGateway.findById(command.pedidoId)
            ?: throw IllegalArgumentException("Pedido não encontrado")

        val produtoPorId = produtoGateway.findAllById(command.itens.map { it.produtoId }).associateBy { it.id!! }

        val pedidoItemList = command.itens.map {
            val produto = produtoPorId[it.produtoId] ?: throw IllegalArgumentException("Produto não encontrado")
            PedidoItem(
                pedidoId = pedido.id!!,
                produto = produto,
                quantidade = it.quantidade,
                precoUnitario = produto.preco
            )
        }

        val valorTotalPedido = pedidoItemList.fold(BigDecimal.ZERO) { acc, item -> acc + item.total() }

        return pedido.copy(status = StatusPedido.PEDIDO_CONFIRMADO, itens = pedidoItemList, total = valorTotalPedido)
            .let { pedidoGateway.save(it) }
            .also { eventPublisher.publish(PedidoConfirmadoEvent(it)) }
    }
}