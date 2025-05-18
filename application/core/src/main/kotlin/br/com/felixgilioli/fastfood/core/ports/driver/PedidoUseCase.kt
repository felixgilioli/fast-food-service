package br.com.felixgilioli.fastfood.core.ports.driver

import br.com.felixgilioli.fastfood.core.commands.ConfirmarPedidoCommand
import br.com.felixgilioli.fastfood.core.commands.NovoPedidoCommand
import br.com.felixgilioli.fastfood.core.entities.Pedido
import java.util.*

interface PedidoUseCase {

    fun novoPedido(command: NovoPedidoCommand): Pedido

    fun confirmarPedido(command: ConfirmarPedidoCommand): Pedido

    fun findPedidosAguardandoConfirmacaoCozinha(): List<Pedido>

    fun confirmarPedidoCozinha(pedidoId: UUID): Pedido

    fun findById(pedidoId: UUID): Pedido?

    fun pedidoPronto(pedidoId: UUID): Pedido

    fun retirarPedido(pedidoId: UUID): Pedido
}