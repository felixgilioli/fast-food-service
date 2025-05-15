package br.com.felixgilioli.fastfood.core.ports.driver

import br.com.felixgilioli.fastfood.core.entities.Produto

interface ProdutoUseCase {

    fun findAll(): List<Produto>
}