package br.com.felixgilioli.fastfood.application.usecases.produto

import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway

class BuscarTodosProdutosUseCase(private val produtoGateway: ProdutoGateway) {

    fun execute() = produtoGateway.findAll()
}