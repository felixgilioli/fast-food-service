package br.com.felixgilioli.fastfood.application.ports.driver

import br.com.felixgilioli.fastfood.application.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.domain.entities.Produto
import java.util.*

interface ProdutoUseCase {

    fun findAll(): List<Produto>

    fun findById(produtoId: UUID): Produto?

    fun findByCategoriaId(categoriaId: UUID): List<Produto>

    fun create(produto: ProdutoCommand): Produto

    fun update(produto: ProdutoCommand): Produto
}