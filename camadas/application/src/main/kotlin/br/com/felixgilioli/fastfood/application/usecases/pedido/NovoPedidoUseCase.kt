package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.application.commands.NovoPedidoCommand
import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.domain.entities.Cliente
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido

class NovoPedidoUseCase(
    private val clienteGateway: ClienteGateway,
    private val pedidoGateway: PedidoGateway
) {

    fun execute(command: NovoPedidoCommand): Pedido {
        var cliente: Cliente? = null

        val clienteNome = when {
            !command.clienteEmail.isNullOrBlank() -> {
                cliente = clienteGateway.findByEmail(command.clienteEmail)
                cliente?.nomeCompleto ?: throw IllegalArgumentException("Cliente não encontrado")
            }

            command.clienteCPF != null -> command.clienteCPF.value
            else -> (10000..99999).random().toString()
        }

        return Pedido(
            status = StatusPedido.CRIADO,
            clienteNome = clienteNome,
            cliente = cliente
        ).let { pedidoGateway.save(it) }
    }
}