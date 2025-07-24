package br.com.felixgilioli.fastfood.application.usecases.produto

import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import java.util.UUID

class BuscarProdutosPelaCategoriaUseCase(private val produtoGateway: ProdutoGateway) {

    fun execute(categoriaId: UUID) = produtoGateway.findByCategoriaId(categoriaId)
}