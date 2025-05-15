package br.com.felixgilioli.fastfood.core.ports.driven

import br.com.felixgilioli.fastfood.core.entities.Produto

interface ProdutoRepository {

    fun findAll(): List<Produto>
}