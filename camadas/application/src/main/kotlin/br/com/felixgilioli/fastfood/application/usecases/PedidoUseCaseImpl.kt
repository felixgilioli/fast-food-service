package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.commands.ConfirmarPedidoCommand
import br.com.felixgilioli.fastfood.application.commands.NovoPedidoCommand
import br.com.felixgilioli.fastfood.domain.entities.Cliente
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.PedidoItem
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.application.events.PedidoConfirmadoEvent
import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.application.ports.driven.PedidoRepository
import br.com.felixgilioli.fastfood.application.ports.driven.ProdutoRepository
import br.com.felixgilioli.fastfood.application.ports.driver.PedidoUseCase
import java.math.BigDecimal
import java.util.*

class PedidoUseCaseImpl(
    private val clienteGateway: ClienteGateway,
    private val pedidoRepository: PedidoRepository,
    private val produtoRepository: ProdutoRepository,
    private val eventPublisher: EventPublisher
) : PedidoUseCase {

    override fun novoPedido(command: NovoPedidoCommand): Pedido {
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
        ).let { pedidoRepository.save(it) }
    }

    override fun confirmarPedido(command: ConfirmarPedidoCommand): Pedido {
        val pedido = pedidoRepository.findById(command.pedidoId)
            ?: throw IllegalArgumentException("Pedido não encontrado")

        val produtoPorId = produtoRepository.findAllById(command.itens.map { it.produtoId }).associateBy { it.id!! }

        val pedidoItemList = command.itens.map {
            val produto = produtoPorId[it.produtoId] ?: throw IllegalArgumentException("Produto não encontrado")
            PedidoItem(
                pedidoId = pedido.id!!,
                produto = produto,
                quantidade = it.quantidade,
                precoUnitario = produto.preco
            )
        }

        val valorTotalPedido = pedidoItemList.fold(BigDecimal.ZERO) { acc, item -> acc + item.total() }

        return pedido.copy(status = StatusPedido.PEDIDO_CONFIRMADO, itens = pedidoItemList, total = valorTotalPedido)
            .let { pedidoRepository.save(it) }
            .also { eventPublisher.publish(PedidoConfirmadoEvent(it)) }
    }

    override fun findPedidosAguardandoConfirmacaoCozinha() =
        pedidoRepository.findByStatus(StatusPedido.PAGAMENTO_APROVADO)
            .sortedBy { it.dataInicio }

    override fun confirmarPedidoCozinha(pedidoId: UUID) = pedidoRepository.findById(pedidoId)
        ?.let { pedidoRepository.save(it.copy(status = StatusPedido.EM_PREPARACAO)) }
        ?: throw IllegalArgumentException("Pedido não encontrado")


    override fun findById(pedidoId: UUID) = pedidoRepository.findById(pedidoId)

    override fun pedidoPronto(pedidoId: UUID) = pedidoRepository.findById(pedidoId)
        ?.let { pedidoRepository.save(it.copy(status = StatusPedido.PRONTO)) }
        ?: throw IllegalArgumentException("Pedido não encontrado")

    override fun retirarPedido(pedidoId: UUID) = pedidoRepository.findById(pedidoId)
        ?.let { pedidoRepository.save(it.copy(status = StatusPedido.FINALIZADO)) }
        ?: throw IllegalArgumentException("Pedido não encontrado")
}