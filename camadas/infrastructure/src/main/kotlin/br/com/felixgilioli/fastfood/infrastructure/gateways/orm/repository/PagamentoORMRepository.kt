package br.com.felixgilioli.fastfood.infrastructure.gateways.orm.repository

import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.PagamentoORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PagamentoORMRepository : JpaRepository<PagamentoORM, UUID> {

    fun findFirstByPedidoIdOrderByDataDesc(pedidoId: UUID): PagamentoORM?
}