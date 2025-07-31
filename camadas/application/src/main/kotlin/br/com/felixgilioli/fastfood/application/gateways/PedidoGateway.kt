package br.com.felixgilioli.fastfood.application.gateways

import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import java.util.*

interface PedidoGateway {

    fun findById(pedidoId: UUID): Pedido?

    fun save(pedido: Pedido): Pedido

    fun findByStatus(status: StatusPedido): List<Pedido>

    fun findByStatusIn(statusList: List<StatusPedido>): List<Pedido>
}