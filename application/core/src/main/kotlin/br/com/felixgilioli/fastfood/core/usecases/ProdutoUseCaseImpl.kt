package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.core.entities.Produto
import br.com.felixgilioli.fastfood.core.ports.driven.ProdutoRepository
import br.com.felixgilioli.fastfood.core.ports.driver.ProdutoUseCase
import java.util.*

class ProdutoUseCaseImpl(private val produtoRepository: ProdutoRepository) : ProdutoUseCase {

    override fun findAll() = produtoRepository.findAll()

    override fun findById(produtoId: UUID) = produtoRepository.findById(produtoId)

    override fun findByCategoriaId(categoriaId: UUID) = produtoRepository.findByCategoriaId(categoriaId)

    override fun create(produto: ProdutoCommand) = produtoRepository.save(produto)

    override fun update(produto: ProdutoCommand) = produtoRepository.save(produto)
}