package br.com.felixgilioli.fastfood.application.usecases.impl

import br.com.felixgilioli.fastfood.domain.entities.Cliente
import br.com.felixgilioli.fastfood.application.exceptions.ClienteAlreadyExistsException
import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.application.usecases.CadastrarClienteUseCase

class CadastrarClienteUseCaseImpl(private val clienteGateway: ClienteGateway) : CadastrarClienteUseCase {

    override fun execute(cliente: Cliente) = clienteGateway.findByEmail(cliente.email)
        ?.also { throw ClienteAlreadyExistsException(cliente.email) }
        .let { clienteGateway.save(cliente) }
}