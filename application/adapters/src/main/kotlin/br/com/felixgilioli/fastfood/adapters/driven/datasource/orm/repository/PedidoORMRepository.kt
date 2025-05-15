package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.PedidoORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PedidoORMRepository : JpaRepository<PedidoORM, UUID> {

}