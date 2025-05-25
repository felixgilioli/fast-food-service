package br.com.felixgilioli.fastfood.core.commands

import java.math.BigDecimal
import java.util.*

data class ProdutoCommand(
    val id: UUID? = null,
    val nome: String,
    val categoriaId: UUID,
    val preco: BigDecimal,
    val imagemUrl: String? = null,
    val descricao: String? = null
)
