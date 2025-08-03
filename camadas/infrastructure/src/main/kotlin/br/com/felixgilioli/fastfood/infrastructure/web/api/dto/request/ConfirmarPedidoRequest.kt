package br.com.felixgilioli.fastfood.infrastructure.web.api.dto.request

import br.com.felixgilioli.fastfood.application.commands.ConfirmarPedidoCommand
import br.com.felixgilioli.fastfood.application.commands.ConfirmarPedidoItemCommand
import java.util.*

data class ConfirmarPedidoRequest(
    val pedidoId: String,
    val itens: List<ConfirmarPedidoItemRequest>
) {
    fun toCommand() = ConfirmarPedidoCommand(
        pedidoId = UUID.fromString(pedidoId),
        itens = itens.map { ConfirmarPedidoItemCommand(UUID.fromString(it.produtoId), it.quantidade) }
    )
}

data class ConfirmarPedidoItemRequest(
    val produtoId: String,
    val quantidade: Int
)
