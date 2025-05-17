package br.com.felixgilioli.fastfood.core.events

import br.com.felixgilioli.fastfood.core.entities.Pedido

data class PedidoConfirmadoEvent(
    val pedido: Pedido
) : Event()
