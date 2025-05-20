package br.com.felixgilioli.fastfood.core.entities

import java.math.BigDecimal
import java.util.*

data class Produto(
    val id: UUID? = null,
    val nome: String,
    val categoria: Categoria,
    val preco: BigDecimal,
    val imagemUrl: String?,
    val descricao: String?
)
