package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.commands.ConfirmarPedidoCommand
import br.com.felixgilioli.fastfood.core.commands.ConfirmarPedidoItemCommand
import br.com.felixgilioli.fastfood.core.commands.NovoPedidoCommand
import br.com.felixgilioli.fastfood.core.entities.*
import br.com.felixgilioli.fastfood.core.events.PedidoConfirmadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.ClienteRepository
import br.com.felixgilioli.fastfood.core.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.core.ports.driven.PedidoRepository
import br.com.felixgilioli.fastfood.core.ports.driven.ProdutoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class PedidoUseCaseImplTest {

    private lateinit var clienteRepository: ClienteRepository
    private lateinit var pedidoRepository: PedidoRepository
    private lateinit var produtoRepository: ProdutoRepository
    private lateinit var eventPublisher: EventPublisher
    private lateinit var pedidoUseCase: PedidoUseCaseImpl

    @BeforeEach
    fun setUp() {
        clienteRepository = mockk()
        pedidoRepository = mockk()
        produtoRepository = mockk()
        eventPublisher = mockk(relaxed = true)
        pedidoUseCase = PedidoUseCaseImpl(clienteRepository, pedidoRepository, produtoRepository, eventPublisher)
    }

    @Test
    fun criaNovoPedidoComClienteExistente() {
        val command = NovoPedidoCommand(clienteEmail = "cliente@email.com", clienteCPF = null)
        val cliente = Cliente(UUID.randomUUID(), "Cliente Teste", "cliente@email.com")
        every { clienteRepository.findByEmail(command.clienteEmail!!) } returns cliente
        every { pedidoRepository.save(any()) } answers { firstArg() }

        val resultado = pedidoUseCase.novoPedido(command)

        assertEquals(cliente.nomeCompleto, resultado.clienteNome)
        assertEquals(StatusPedido.CRIADO, resultado.status)
        verify { clienteRepository.findByEmail(command.clienteEmail!!) }
        verify { pedidoRepository.save(any()) }
    }

    @Test
    fun lancaExcecaoQuandoClienteNaoEncontrado() {
        val command = NovoPedidoCommand(clienteEmail = "inexistente@email.com", clienteCPF = null)
        every { clienteRepository.findByEmail(command.clienteEmail!!) } returns null

        val excecao = assertThrows(IllegalArgumentException::class.java) {
            pedidoUseCase.novoPedido(command)
        }

        assertEquals("Cliente não encontrado", excecao.message)
        verify { clienteRepository.findByEmail(command.clienteEmail!!) }
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
        every { pedidoRepository.findById(pedidoId) } returns pedido
        every { produtoRepository.findAllById(command.itens.map { it.produtoId }) } returns listOf(produto)
        every { pedidoRepository.save(any()) } answers { firstArg() }

        val resultado = pedidoUseCase.confirmarPedido(command)

        assertEquals(StatusPedido.PEDIDO_CONFIRMADO, resultado.status)
        assertEquals(1, resultado.itens.size)
        assertEquals(BigDecimal(20), resultado.total)
        verify { pedidoRepository.findById(pedidoId) }
        verify { produtoRepository.findAllById(command.itens.map { it.produtoId }) }
        verify { pedidoRepository.save(any()) }
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
        every { pedidoRepository.findById(pedidoId) } returns pedido
        every { produtoRepository.findAllById(command.itens.map { it.produtoId }) } returns emptyList()

        val excecao = assertThrows(IllegalArgumentException::class.java) {
            pedidoUseCase.confirmarPedido(command)
        }

        assertEquals("Produto não encontrado", excecao.message)
        verify { pedidoRepository.findById(pedidoId) }
        verify { produtoRepository.findAllById(command.itens.map { it.produtoId }) }
    }

    @Test
    fun retornaPedidosAguardandoConfirmacaoCozinha() {
        val pedidos = listOf(
            Pedido(id = UUID.randomUUID(), status = StatusPedido.PAGAMENTO_APROVADO, clienteNome = "Cliente 1"),
            Pedido(id = UUID.randomUUID(), status = StatusPedido.PAGAMENTO_APROVADO, clienteNome = "Cliente 2")
        )
        every { pedidoRepository.findByStatus(StatusPedido.PAGAMENTO_APROVADO) } returns pedidos

        val resultado = pedidoUseCase.findPedidosAguardandoConfirmacaoCozinha()

        assertEquals(2, resultado.size)
        assertEquals(StatusPedido.PAGAMENTO_APROVADO, resultado[0].status)
        verify { pedidoRepository.findByStatus(StatusPedido.PAGAMENTO_APROVADO) }
    }
}