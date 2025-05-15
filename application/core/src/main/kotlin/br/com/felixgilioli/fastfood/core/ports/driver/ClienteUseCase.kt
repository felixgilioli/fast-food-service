package br.com.felixgilioli.fastfood.core.ports.driver

import br.com.felixgilioli.fastfood.core.entities.Cliente

interface ClienteUseCase {

    fun cadastrar(cliente: Cliente): Cliente
}