package br.com.felixgilioli.fastfood.infrastructure.gateways

import br.com.felixgilioli.fastfood.application.ports.driven.PedidoRepository
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.repository.PedidoORMRepository
import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.toOrm
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