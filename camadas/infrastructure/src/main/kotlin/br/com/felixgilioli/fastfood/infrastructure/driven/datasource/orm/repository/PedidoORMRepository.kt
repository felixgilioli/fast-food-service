package br.com.felixgilioli.fastfood.infrastructure.driven.datasource.orm.repository

import br.com.felixgilioli.fastfood.infrastructure.driven.datasource.orm.PedidoORM
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PedidoORMRepository : JpaRepository<PedidoORM, UUID> {

    fun findByStatus(status: StatusPedido): List<PedidoORM>

}