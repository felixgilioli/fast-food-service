package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.domain.entities.Pagamento
import br.com.felixgilioli.fastfood.domain.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class RecusarPagamentoUseCaseTest {

    private lateinit var pagamentoGateway: PagamentoGateway
    private lateinit var eventPublisher: EventPublisher
    private lateinit var useCase: RecusarPagamentoUseCase

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
        pagamentoGateway = mockk()
        eventPublisher = mockk(relaxed = true)
        useCase = RecusarPagamentoUseCase(pagamentoGateway, eventPublisher)
    }

    @Test
    fun `deve recusar pagamento e publicar evento`() {
        every { pagamentoGateway.findById(pagamentoId) } returns pagamento
        every { pagamentoGateway.insert(any()) } answers { firstArg() }
        every { eventPublisher.publish(any()) } just Runs

        useCase.execute(pagamentoId)

        verify { pagamentoGateway.findById(pagamentoId) }
        verify { pagamentoGateway.insert(any()) }
        verify { eventPublisher.publish(match { it is PagamentoRecusadoEvent }) }
    }

}