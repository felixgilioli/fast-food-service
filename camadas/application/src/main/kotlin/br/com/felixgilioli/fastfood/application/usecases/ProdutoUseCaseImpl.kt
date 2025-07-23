package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.application.ports.driven.ProdutoRepository
import br.com.felixgilioli.fastfood.application.ports.driver.ProdutoUseCase
import java.util.*

class ProdutoUseCaseImpl(private val produtoRepository: ProdutoRepository) : ProdutoUseCase {

    override fun findAll() = produtoRepository.findAll()

    override fun findById(produtoId: UUID) = produtoRepository.findById(produtoId)

    override fun findByCategoriaId(categoriaId: UUID) = produtoRepository.findByCategoriaId(categoriaId)

    override fun create(produto: ProdutoCommand) = produtoRepository.save(produto)

    override fun update(produto: ProdutoCommand) = produtoRepository.save(produto)
}