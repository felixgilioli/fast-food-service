package br.com.felixgilioli.fastfood.core.events

import br.com.felixgilioli.fastfood.core.entities.Pedido

data class LinkPagamentoCriadoEvent(
    val pedido: Pedido,
    val linkPagamento: String
) : Event()
