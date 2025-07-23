package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway

class AtualizarProdutoUseCase(private val produtoGateway: ProdutoGateway) {

    fun execute(produto: ProdutoCommand) = produtoGateway.save(produto)
}