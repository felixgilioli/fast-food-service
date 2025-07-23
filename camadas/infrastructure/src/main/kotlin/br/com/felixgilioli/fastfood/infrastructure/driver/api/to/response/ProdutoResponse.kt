package br.com.felixgilioli.fastfood.infrastructure.driver.api.to.response

import br.com.felixgilioli.fastfood.application.entities.Produto
import java.math.BigDecimal

data class ProdutoResponse(
    val produtoId: String,
    val nome: String,
    val preco: BigDecimal,
    val imagemUrl: String? = null,
    val descricao: String? = null
)

fun Produto.toResponse() = ProdutoResponse(
    produtoId = this.id.toString(),
    nome = this.nome,
    preco = this.preco,
    imagemUrl = this.imagemUrl,
    descricao = this.descricao
)