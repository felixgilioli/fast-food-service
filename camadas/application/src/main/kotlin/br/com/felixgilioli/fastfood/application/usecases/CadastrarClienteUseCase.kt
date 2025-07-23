package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.domain.entities.Cliente

interface CadastrarClienteUseCase {

    fun execute(cliente: Cliente): Cliente
}