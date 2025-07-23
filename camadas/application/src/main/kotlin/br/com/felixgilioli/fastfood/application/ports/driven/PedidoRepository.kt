package br.com.felixgilioli.fastfood.application.ports.driven

import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import java.util.*

interface PedidoRepository {

    fun findById(pedidoId: UUID): Pedido?

    fun save(pedido: Pedido): Pedido

    fun findByStatus(status: StatusPedido): List<Pedido>
}