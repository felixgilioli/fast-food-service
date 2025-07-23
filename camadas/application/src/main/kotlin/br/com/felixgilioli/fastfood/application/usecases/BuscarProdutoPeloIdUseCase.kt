package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import java.util.UUID

class BuscarProdutoPeloIdUseCase(private val produtoGateway: ProdutoGateway) {

    fun execute(produtoId: UUID) = produtoGateway.findById(produtoId)
}