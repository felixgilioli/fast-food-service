package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.PedidoORM
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PedidoORMRepository : JpaRepository<PedidoORM, UUID> {

    fun findByStatus(status: StatusPedido): List<PedidoORM>

}