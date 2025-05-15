package br.com.felixgilioli.fastfood.core.commands

import br.com.felixgilioli.fastfood.core.entities.CPF

data class NovoPedidoCommand(
    val clienteEmail: String?,
    val clienteCPF: CPF?
)
