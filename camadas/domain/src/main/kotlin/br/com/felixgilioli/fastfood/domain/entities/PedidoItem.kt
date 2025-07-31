package br.com.felixgilioli.fastfood.domain.entities

import java.math.BigDecimal
import java.util.UUID

data class PedidoItem(
    val id: UUID? = null,
    val pedidoId: UUID,
    val produto: Produto,
    val quantidade: Int,
    val precoUnitario: BigDecimal
) {
    fun total() = precoUnitario.multiply(quantidade.toBigDecimal())
}