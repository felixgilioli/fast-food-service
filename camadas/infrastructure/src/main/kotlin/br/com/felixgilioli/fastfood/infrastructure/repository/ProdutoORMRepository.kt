package br.com.felixgilioli.fastfood.infrastructure.repository

import br.com.felixgilioli.fastfood.infrastructure.orm.ProdutoORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProdutoORMRepository : JpaRepository<ProdutoORM, UUID> {

    fun findByCategoriaId(categoriaId: UUID): List<ProdutoORM>
}