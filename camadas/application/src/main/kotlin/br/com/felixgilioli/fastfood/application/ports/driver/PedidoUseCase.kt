package br.com.felixgilioli.fastfood.application.ports.driver

import br.com.felixgilioli.fastfood.domain.entities.Pedido
import java.util.*

interface PedidoUseCase {

    fun retirarPedido(pedidoId: UUID): Pedido
}