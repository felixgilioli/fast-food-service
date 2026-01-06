package br.com.felixgilioli.fastfood.application.usecases.produto

import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import java.util.*

class BuscarTodosProdutosUseCase(private val produtoGateway: ProdutoGateway) {

    fun execute(ids: List<UUID>? = null) =
        if (ids == null) produtoGateway.findAll()
        else produtoGateway.findAllById(ids)
}