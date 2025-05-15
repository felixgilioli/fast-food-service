package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.entities.Cliente
import br.com.felixgilioli.fastfood.core.exceptions.ClienteAlreadyExistsException
import br.com.felixgilioli.fastfood.core.ports.driven.ClienteRepository
import br.com.felixgilioli.fastfood.core.ports.driver.ClienteUseCase

class ClienteUseCaseImpl(private val clienteRepository: ClienteRepository) : ClienteUseCase {

    override fun cadastrar(cliente: Cliente) = clienteRepository.findByEmail(cliente.email)
        ?.also { throw ClienteAlreadyExistsException(cliente.email) }
        .let { clienteRepository.save(cliente) }
}