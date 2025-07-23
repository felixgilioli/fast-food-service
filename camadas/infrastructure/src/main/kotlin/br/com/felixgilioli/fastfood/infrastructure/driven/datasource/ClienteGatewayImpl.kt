package br.com.felixgilioli.fastfood.infrastructure.driven.datasource

import br.com.felixgilioli.fastfood.infrastructure.driven.datasource.orm.repository.ClienteORMRepository
import br.com.felixgilioli.fastfood.infrastructure.driven.datasource.orm.toORM
import br.com.felixgilioli.fastfood.application.entities.Cliente
import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import org.springframework.stereotype.Service

@Service
class ClienteGatewayImpl(private val clienteORMRepository: ClienteORMRepository) : ClienteGateway {

    override fun findByEmail(email: String) = clienteORMRepository.findByEmail(email)?.toDomain()

    override fun save(cliente: Cliente) = clienteORMRepository.save(cliente.toORM()).toDomain()

}