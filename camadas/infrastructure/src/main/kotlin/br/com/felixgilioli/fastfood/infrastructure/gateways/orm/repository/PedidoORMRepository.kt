package br.com.felixgilioli.fastfood.infrastructure.gateways.orm.repository

import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.PedidoORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PedidoORMRepository : JpaRepository<PedidoORM, UUID> {

    fun findByStatus(status: StatusPedido): List<PedidoORM>

    fun findByStatusIn(statusList: List<StatusPedido>): List<PedidoORM>

}