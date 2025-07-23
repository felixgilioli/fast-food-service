package br.com.felixgilioli.fastfood.application.events

import br.com.felixgilioli.fastfood.domain.entities.Pedido

data class LinkPagamentoCriadoEvent(
    val pedido: Pedido,
    val linkPagamento: String
) : Event()
