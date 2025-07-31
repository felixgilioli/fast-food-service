package br.com.felixgilioli.fastfood.application.gateways

import br.com.felixgilioli.fastfood.domain.entities.Cliente

interface ClienteGateway {

    fun findByEmail(email: String): Cliente?

    fun save(cliente: Cliente): Cliente
}