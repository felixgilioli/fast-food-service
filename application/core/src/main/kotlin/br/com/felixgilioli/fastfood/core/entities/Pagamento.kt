package br.com.felixgilioli.fastfood.core.entities

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Pagamento(
    val id: UUID? = null,
    val pedido: Pedido,
    val valor: BigDecimal,
    val data: LocalDateTime = LocalDateTime.now(),
    val status: PagamentoStatus,
    val link: String
)
