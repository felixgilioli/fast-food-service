package br.com.felixgilioli.fastfood.infrastructure.gateways

import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.repository.ClienteORMRepository
import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.toORM
import br.com.felixgilioli.fastfood.domain.entities.Cliente
import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import org.springframework.stereotype.Service

@Service
class ClienteGatewayImpl(private val clienteORMRepository: ClienteORMRepository) : ClienteGateway {

    override fun findByEmail(email: String) = clienteORMRepository.findByEmail(email)?.toDomain()

    override fun save(cliente: Cliente) = clienteORMRepository.save(cliente.toORM()).toDomain()

}