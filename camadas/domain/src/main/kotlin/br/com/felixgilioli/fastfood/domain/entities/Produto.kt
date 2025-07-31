package br.com.felixgilioli.fastfood.domain.entities

import java.math.BigDecimal
import java.util.UUID

data class Produto(
    val id: UUID? = null,
    val nome: String,
    val categoria: Categoria,
    val preco: BigDecimal,
    val imagemUrl: String? = null,
    val descricao: String? = null
)