package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.ClienteORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClienteORMRepository : JpaRepository<ClienteORM, UUID> {

    fun findByEmail(email: String): ClienteORM?
}