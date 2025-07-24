package br.com.felixgilioli.fastfood.application.usecases.cliente

import br.com.felixgilioli.fastfood.application.exceptions.ClienteAlreadyExistsException
import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.domain.entities.Cliente

class CadastrarClienteUseCase(private val clienteGateway: ClienteGateway) {

    fun execute(cliente: Cliente) = clienteGateway.findByEmail(cliente.email)
        ?.also { throw ClienteAlreadyExistsException(cliente.email) }
        .let { clienteGateway.save(cliente) }
}