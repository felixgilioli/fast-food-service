package br.com.felixgilioli.fastfood.core.ports.driven

import br.com.felixgilioli.fastfood.core.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.core.entities.Produto
import java.util.*

interface ProdutoRepository {

    fun findAll(): List<Produto>

    fun findAllById(produtoIds: List<UUID>): List<Produto>

    fun findById(produtoId: UUID): Produto?

    fun save(produto: ProdutoCommand): Produto
}