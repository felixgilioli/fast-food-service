package br.com.felixgilioli.fastfood.infrastructure.repository

import br.com.felixgilioli.fastfood.infrastructure.orm.ClienteORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClienteORMRepository : JpaRepository<ClienteORM, UUID> {

    fun findByEmail(email: String): ClienteORM?
}