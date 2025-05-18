package br.com.felixgilioli.fastfood.adapters.driver.api.to.response

import br.com.felixgilioli.fastfood.core.entities.Produto
import java.math.BigDecimal

data class ProdutoResponse(
    val id: String,
    val nome: String,
    val preco: BigDecimal,
    val descricao: String? = null
)

fun Produto.toResponse() = ProdutoResponse(
    id = this.id.toString(),
    nome = this.nome,
    preco = this.preco,
    descricao = this.descricao
)