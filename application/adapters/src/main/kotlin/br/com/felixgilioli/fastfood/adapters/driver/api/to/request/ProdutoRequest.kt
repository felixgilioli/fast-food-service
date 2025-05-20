package br.com.felixgilioli.fastfood.adapters.driver.api.to.request

import br.com.felixgilioli.fastfood.core.commands.ProdutoCommand
import java.math.BigDecimal
import java.util.*

data class ProdutoRequest(
    val nome: String,
    val categoriaId: String,
    val preco: BigDecimal,
    val imagemUrl: String?,
    val descricao: String?
) {
    fun toCommand(produtoId: String? = null) = ProdutoCommand(
        id = UUID.fromString(produtoId),
        nome = nome,
        categoriaId = UUID.fromString(categoriaId),
        preco = preco,
        imagemUrl = imagemUrl,
        descricao = descricao
    )
}