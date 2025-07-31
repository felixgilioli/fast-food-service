package br.com.felixgilioli.fastfood.application.events

import br.com.felixgilioli.fastfood.domain.entities.Pedido

data class PagamentoAprovadoEvent(
    val pedido: Pedido
) : Event()
