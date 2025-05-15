package br.com.felixgilioli.fastfood.adapters.driven.datasource

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository.ClienteORMRepository
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.toORM
import br.com.felixgilioli.fastfood.core.entities.Cliente
import br.com.felixgilioli.fastfood.core.ports.driven.ClienteRepository
import org.springframework.stereotype.Service

@Service
class ClienteDataSource(private val clienteORMRepository: ClienteORMRepository) : ClienteRepository {

    override fun findByEmail(email: String) = clienteORMRepository.findByEmail(email)?.toDomain()

    override fun save(cliente: Cliente) = clienteORMRepository.save(cliente.toORM()).toDomain()

}