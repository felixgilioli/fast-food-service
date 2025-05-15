package br.com.felixgilioli.fastfood.core.entities

import java.math.BigDecimal
import java.util.*

data class PedidoItem(
    val id: UUID? = null,
    val pedidoId: UUID,
    val produto: Produto,
    val quantidade: Int,
    val precoUnitario: BigDecimal
) {
    fun total() = precoUnitario.multiply(quantidade.toBigDecimal())
}
