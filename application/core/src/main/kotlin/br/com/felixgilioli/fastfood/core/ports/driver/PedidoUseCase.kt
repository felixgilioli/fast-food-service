package br.com.felixgilioli.fastfood.core.ports.driver

import br.com.felixgilioli.fastfood.core.commands.ConfirmarPedidoCommand
import br.com.felixgilioli.fastfood.core.commands.NovoPedidoCommand
import br.com.felixgilioli.fastfood.core.entities.CPF
import br.com.felixgilioli.fastfood.core.entities.Pedido

interface PedidoUseCase {

    fun novoPedido(command: NovoPedidoCommand): Pedido

    fun confirmarPedido(command: ConfirmarPedidoCommand): Pedido
}