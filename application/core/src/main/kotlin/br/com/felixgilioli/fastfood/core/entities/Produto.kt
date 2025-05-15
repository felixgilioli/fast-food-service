package br.com.felixgilioli.fastfood.core.entities

import java.math.BigDecimal
import java.util.*

data class Produto(
    val id: UUID?,
    val nome: String,
    val categoria: Categoria,
    val preco: BigDecimal,
    val descricao: String?
)
