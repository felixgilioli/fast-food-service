package br.com.felixgilioli.fastfood.core.ports.driver

import br.com.felixgilioli.fastfood.core.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.core.entities.Produto
import java.util.*

interface ProdutoUseCase {

    fun findAll(): List<Produto>

    fun findById(produtoId: UUID): Produto?

    fun findByCategoriaId(categoriaId: UUID): List<Produto>

    fun create(produto: ProdutoCommand): Produto

    fun update(produto: ProdutoCommand): Produto
}