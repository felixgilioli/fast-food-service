package br.com.felixgilioli.fastfood.core.ports.driven

import br.com.felixgilioli.fastfood.core.entities.Pedido
import java.util.*

interface PedidoRepository {

    fun findById(pedidoId: UUID): Pedido?

    fun save(pedido: Pedido): Pedido
}