package br.com.felixgilioli.fastfood.infrastructure.driver.api.to.request

import br.com.felixgilioli.fastfood.application.commands.NovoPedidoCommand
import br.com.felixgilioli.fastfood.domain.entities.CPF

data class NovoPedidoRequest(
    val clienteEmail: String? = null,
    val clienteCPF: String? = null
) {
    fun toCommand() = NovoPedidoCommand(
        clienteEmail = clienteEmail,
        clienteCPF = clienteCPF?.let { CPF(it) }
    )
}
