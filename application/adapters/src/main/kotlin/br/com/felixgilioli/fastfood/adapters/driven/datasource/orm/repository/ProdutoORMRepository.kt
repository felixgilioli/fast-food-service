package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.ProdutoORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProdutoORMRepository : JpaRepository<ProdutoORM, UUID> {

    fun findByCategoriaId(categoriaId: UUID): List<ProdutoORM>
}