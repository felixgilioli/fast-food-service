package br.com.felixgilioli.fastfood.core.ports.driven

import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import java.util.*

interface PedidoRepository {

    fun findById(pedidoId: UUID): Pedido?

    fun save(pedido: Pedido): Pedido

    fun findByStatus(status: StatusPedido): List<Pedido>
}