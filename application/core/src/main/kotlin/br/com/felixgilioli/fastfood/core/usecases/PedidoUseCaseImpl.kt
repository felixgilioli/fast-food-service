package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.commands.NovoPedidoCommand
import br.com.felixgilioli.fastfood.core.entities.Cliente
import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import br.com.felixgilioli.fastfood.core.ports.driven.ClienteRepository
import br.com.felixgilioli.fastfood.core.ports.driven.PedidoRepository
import br.com.felixgilioli.fastfood.core.ports.driver.PedidoUseCase

class PedidoUseCaseImpl(
    private val clienteRepository: ClienteRepository,
    private val pedidoRepository: PedidoRepository
) : PedidoUseCase {

    override fun novoPedido(novoPedidoCommand: NovoPedidoCommand): Pedido {
        var cliente: Cliente? = null

        val clienteNome = when {
            !novoPedidoCommand.clienteEmail.isNullOrBlank() -> {
                cliente = clienteRepository.findByEmail(novoPedidoCommand.clienteEmail)
                cliente?.nomeCompleto ?: throw IllegalArgumentException("Cliente não encontrado")
            }

            novoPedidoCommand.clienteCPF != null -> novoPedidoCommand.clienteCPF.value
            else -> (10000..99999).random().toString()
        }

        return Pedido(
            status = StatusPedido.CRIADO,
            clienteNome = clienteNome,
            cliente = cliente
        ).let { pedidoRepository.save(it) }
    }

}