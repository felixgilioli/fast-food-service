package br.com.felixgilioli.fastfood.adapters.driven.datasource

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository.PedidoORMRepository
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.toOrm
import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.ports.driven.PedidoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class PedidoDataSource(private val pedidoORMRepository: PedidoORMRepository) : PedidoRepository {

    override fun findById(pedidoId: UUID) = pedidoORMRepository.findByIdOrNull(pedidoId)
        ?.toDomain()

    override fun save(pedido: Pedido) = pedidoORMRepository.save(pedido.toOrm()).toDomain()
}