package br.com.felixgilioli.fastfood.adapters.driver.api.to.response

import java.math.BigDecimal

data class ProdutosPorCategoriaResponse(
    val categoria: String,
    val produtos: List<ProdutoResponse>
)

data class ProdutoResponse(
    val id: String,
    val nome: String,
    val preco: BigDecimal,
    val descricao: String? = null
)
