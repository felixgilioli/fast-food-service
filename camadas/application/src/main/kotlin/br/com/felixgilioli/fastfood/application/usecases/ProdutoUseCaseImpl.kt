package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import br.com.felixgilioli.fastfood.application.ports.driver.ProdutoUseCase

class ProdutoUseCaseImpl(private val produtoGateway: ProdutoGateway) : ProdutoUseCase {

    override fun update(produto: ProdutoCommand) = produtoGateway.save(produto)
}