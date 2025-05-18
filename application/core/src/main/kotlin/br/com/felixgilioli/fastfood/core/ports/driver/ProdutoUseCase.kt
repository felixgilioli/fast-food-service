package br.com.felixgilioli.fastfood.core.ports.driver

import br.com.felixgilioli.fastfood.core.entities.Produto
import java.util.*

interface ProdutoUseCase {

    fun findAll(): List<Produto>

    fun findById(produtoId: UUID): Produto?
}