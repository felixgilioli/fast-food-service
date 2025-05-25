package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.entities.Pagamento
import br.com.felixgilioli.fastfood.core.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import br.com.felixgilioli.fastfood.core.events.PagamentoAprovadoEvent
import br.com.felixgilioli.fastfood.core.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.core.ports.driven.PagamentoRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class PagamentoUseCaseImplTest {

    private lateinit var pagamentoRepository: PagamentoRepository
    private lateinit var eventPublisher: EventPublisher
    private lateinit var useCase: PagamentoUseCaseImpl

    private val pedidoId = UUID.randomUUID()
    private val pagamentoId = UUID.randomUUID()

    private val pedido = Pedido(
        id = pedidoId,
        status = StatusPedido.CRIADO,
        clienteNome = "Fulano",
        total = BigDecimal.ZERO
    )

    private val pagamento = Pagamento(
        pagamentoId,
        pedido,
        BigDecimal.ZERO,
        LocalDateTime.now(),
        PagamentoStatus.LINK_PAGAMENTO_GERADO,
        "http://link-pagamento.com"
    )

    @BeforeEach
    fun setUp() {
        pagamentoRepository = mockk()
        eventPublisher = mockk(relaxed = true)
        useCase = PagamentoUseCaseImpl(pagamentoRepository, eventPublisher)
    }

    @Test
    fun `deve retornar pagamento pelo pedido`() {
        every { pagamentoRepository.findLastByPedidoId(pedidoId) } returns pagamento

        val result = useCase.getPagamentoByPedido(pedidoId)

        assertEquals(pagamento, result)
        verify { pagamentoRepository.findLastByPedidoId(pedidoId) }
    }

    @Test
    fun `deve aprovar pagamento e publicar evento`() {
        every { pagamentoRepository.findById(pagamentoId) } returns pagamento
        every { pagamentoRepository.insert(any()) } answers { firstArg() }
        every { eventPublisher.publish(any()) } just Runs

        useCase.aprovarPagamento(pagamentoId)

        verify { pagamentoRepository.findById(pagamentoId) }
        verify { pagamentoRepository.insert(any()) }
        verify { eventPublisher.publish(match { it is PagamentoAprovadoEvent }) }
    }

    @Test
    fun `deve recusar pagamento e publicar evento`() {
        every { pagamentoRepository.findById(pagamentoId) } returns pagamento
        every { pagamentoRepository.insert(any()) } answers { firstArg() }
        every { eventPublisher.publish(any()) } just Runs

        useCase.recusarPagamento(pagamentoId)

        verify { pagamentoRepository.findById(pagamentoId) }
        verify { pagamentoRepository.insert(any()) }
        verify { eventPublisher.publish(match { it is PagamentoRecusadoEvent }) }
    }

    @Test
    fun `deve lançar exceção se pagamento não encontrado`() {
        every { pagamentoRepository.findById(pagamentoId) } returns null

        val ex = assertThrows(IllegalArgumentException::class.java) {
            useCase.aprovarPagamento(pagamentoId)
        }
        assertEquals("Pagamento não encontrado", ex.message)
    }
}