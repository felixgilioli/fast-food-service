package br.com.felixgilioli.fastfood.infrastructure.driven.datasource

import br.com.felixgilioli.fastfood.infrastructure.driven.datasource.orm.repository.PedidoORMRepository
import br.com.felixgilioli.fastfood.infrastructure.driven.datasource.orm.toOrm
import br.com.felixgilioli.fastfood.application.entities.Pedido
import br.com.felixgilioli.fastfood.application.entities.StatusPedido
import br.com.felixgilioli.fastfood.application.ports.driven.PedidoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class PedidoDataSource(private val pedidoORMRepository: PedidoORMRepository) : PedidoRepository {

    override fun findById(pedidoId: UUID) = pedidoORMRepository.findByIdOrNull(pedidoId)
        ?.toDomain()

    override fun save(pedido: Pedido) = pedidoORMRepository.save(pedido.toOrm()).toDomain()

    override fun findByStatus(status: StatusPedido) = pedidoORMRepository.findByStatus(status)
        .map { it.toDomain() }
}