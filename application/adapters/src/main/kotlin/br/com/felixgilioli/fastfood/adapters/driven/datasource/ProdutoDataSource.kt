package br.com.felixgilioli.fastfood.adapters.driven.datasource

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository.ProdutoORMRepository
import br.com.felixgilioli.fastfood.core.ports.driven.ProdutoRepository
import org.springframework.stereotype.Service

@Service
class ProdutoDataSource(private val produtoORMRepository: ProdutoORMRepository) : ProdutoRepository {

    override fun findAll() = produtoORMRepository.findAll().map { it.toDomain() }
}