package br.com.felixgilioli.fastfood.application.ports.driver

import br.com.felixgilioli.fastfood.application.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.domain.entities.Produto

interface ProdutoUseCase {

    fun create(produto: ProdutoCommand): Produto

    fun update(produto: ProdutoCommand): Produto
}