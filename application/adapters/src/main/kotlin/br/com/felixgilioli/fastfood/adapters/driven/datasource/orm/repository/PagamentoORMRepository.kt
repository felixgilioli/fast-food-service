package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.PagamentoORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PagamentoORMRepository : JpaRepository<PagamentoORM, UUID> {
}