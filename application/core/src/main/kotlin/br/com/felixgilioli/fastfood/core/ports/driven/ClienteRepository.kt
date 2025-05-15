package br.com.felixgilioli.fastfood.core.ports.driven

import br.com.felixgilioli.fastfood.core.entities.Cliente

interface ClienteRepository {

    fun findByEmail(email: String): Cliente?

    fun save(cliente: Cliente): Cliente
}