package br.com.felixgilioli.fastfood.application.commands

import br.com.felixgilioli.fastfood.domain.entities.CPF

data class NovoPedidoCommand(
    val clienteEmail: String?,
    val clienteCPF: CPF?
)
