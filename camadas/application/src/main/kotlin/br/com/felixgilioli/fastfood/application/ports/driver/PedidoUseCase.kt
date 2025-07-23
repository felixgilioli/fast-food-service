package br.com.felixgilioli.fastfood.application.ports.driver

import br.com.felixgilioli.fastfood.application.commands.ConfirmarPedidoCommand
import br.com.felixgilioli.fastfood.application.commands.NovoPedidoCommand
import br.com.felixgilioli.fastfood.domain.entities.Pedido
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