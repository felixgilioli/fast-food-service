package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.ports.driven.ProdutoRepository
import br.com.felixgilioli.fastfood.core.ports.driver.ProdutoUseCase

class ProdutoUseCaseImpl(private val produtoRepository: ProdutoRepository) : ProdutoUseCase {

    override fun findAll() = produtoRepository.findAll()
}