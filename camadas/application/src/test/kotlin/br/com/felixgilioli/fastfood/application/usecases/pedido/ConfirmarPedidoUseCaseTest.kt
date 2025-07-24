package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.application.commands.ConfirmarPedidoCommand
import br.com.felixgilioli.fastfood.application.commands.ConfirmarPedidoItemCommand
import br.com.felixgilioli.fastfood.application.events.PedidoConfirmadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.domain.entities.Categoria
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.Produto
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class ConfirmarPedidoUseCaseTest {

    private lateinit var pedidoGateway: PedidoGateway
    private lateinit var produtoGateway: ProdutoGateway
    private lateinit var eventPublisher: EventPublisher
    private lateinit var useCase: ConfirmarPedidoUseCase

    @BeforeEach
    fun setUp() {
        pedidoGateway = mockk()
        produtoGateway = mockk()
        eventPublisher = mockk(relaxed = true)
        useCase = ConfirmarPedidoUseCase(pedidoGateway, produtoGateway, eventPublisher)
    }

    @Test
    fun confirmaPedidoComItensValidos() {
        val pedidoId = UUID.randomUUID()
        val pedido = Pedido(
            id = pedidoId,
            status = StatusPedido.CRIADO,
            clienteNome = "Cliente Teste"
        )
        val produto = Produto(
            id = UUID.randomUUID(),
            nome = "Produto Teste",
            preco = BigDecimal.TEN,
            categoria = Categoria(descricao = "teste")
        )

        val command = ConfirmarPedidoCommand(
            pedidoId = pedidoId,
            itens = listOf(ConfirmarPedidoItemCommand(produto.id!!, 2))
        )
        every { pedidoGateway.findById(pedidoId) } returns pedido
        every { produtoGateway.findAllById(command.itens.map { it.produtoId }) } returns listOf(produto)
        every { pedidoGateway.save(any()) } answers { firstArg() }

        val resultado = useCase.execute(command)

        assertEquals(StatusPedido.PEDIDO_CONFIRMADO, resultado.status)
        assertEquals(1, resultado.itens.size)
        assertEquals(BigDecimal(20), resultado.total)
        verify { pedidoGateway.findById(pedidoId) }
        verify { produtoGateway.findAllById(command.itens.map { it.produtoId }) }
        verify { pedidoGateway.save(any()) }
        verify { eventPublisher.publish(any<PedidoConfirmadoEvent>()) }
    }

    @Test
    fun lancaExcecaoQuandoProdutoNaoEncontrado() {
        val pedidoId = UUID.randomUUID()
        val command = ConfirmarPedidoCommand(
            pedidoId = pedidoId,
            itens = listOf(ConfirmarPedidoItemCommand(UUID.randomUUID(), 2))
        )
        val pedido = Pedido(
            id = pedidoId,
            status = StatusPedido.CRIADO,
            clienteNome = "Cliente Teste"
        )
        every { pedidoGateway.findById(pedidoId) } returns pedido
        every { produtoGateway.findAllById(command.itens.map { it.produtoId }) } returns emptyList()

        val excecao = assertThrows(IllegalArgumentException::class.java) {
            useCase.execute(command)
        }

        assertEquals("Produto não encontrado", excecao.message)
        verify { pedidoGateway.findById(pedidoId) }
        verify { produtoGateway.findAllById(command.itens.map { it.produtoId }) }
    }

}