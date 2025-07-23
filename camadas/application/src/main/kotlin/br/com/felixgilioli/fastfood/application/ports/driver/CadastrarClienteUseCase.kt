package br.com.felixgilioli.fastfood.application.ports.driver

import br.com.felixgilioli.fastfood.domain.entities.Cliente

interface CadastrarClienteUseCase {

    fun execute(cliente: Cliente): Cliente
}