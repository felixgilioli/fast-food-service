package br.com.felixgilioli.fastfood.application.gateways

import br.com.felixgilioli.fastfood.application.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.domain.entities.Produto
import java.util.UUID

interface ProdutoGateway {

    fun findAll(): List<Produto>

    fun findAllById(produtoIds: List<UUID>): List<Produto>

    fun findById(produtoId: UUID): Produto?

    fun save(produto: ProdutoCommand): Produto

    fun findByCategoriaId(categoriaId: UUID): List<Produto>
}