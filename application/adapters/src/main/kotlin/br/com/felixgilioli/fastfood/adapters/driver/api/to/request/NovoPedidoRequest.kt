package br.com.felixgilioli.fastfood.adapters.driver.api.to.request

import br.com.felixgilioli.fastfood.core.commands.NovoPedidoCommand
import br.com.felixgilioli.fastfood.core.entities.CPF

data class NovoPedidoRequest(
    val clienteEmail: String? = null,
    val clienteCPF: String? = null
) {
    fun toCommand() = NovoPedidoCommand(
        clienteEmail = clienteEmail,
        clienteCPF = clienteCPF?.let { CPF(it) }
    )
}
