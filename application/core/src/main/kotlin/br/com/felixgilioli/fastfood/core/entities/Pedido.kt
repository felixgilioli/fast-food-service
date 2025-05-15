package br.com.felixgilioli.fastfood.core.entities

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Pedido(
    val id: UUID? = null,
    val status: StatusPedido,
    val dataInicio: LocalDateTime = LocalDateTime.now(),
    val dataFim: LocalDateTime? = null,
    val clienteNome: String,
    val cliente: Cliente? = null,
    val total: BigDecimal? = null
)
