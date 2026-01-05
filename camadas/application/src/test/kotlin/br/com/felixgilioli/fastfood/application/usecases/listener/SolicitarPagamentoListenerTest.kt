package br.com.felixgilioli.fastfood.application.usecases.listener

import br.com.felixgilioli.fastfood.application.events.EventPublisher
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class SolicitarPagamentoListenerTest {

    private lateinit var pagamentoGateway: PagamentoGateway
    private lateinit var eventPublisher: EventPublisher
    private lateinit var listener: SolicitarPagamentoListener

    @BeforeEach
    fun setUp() {
        pagamentoGateway = mockk()
        eventPublisher = mockk(relaxed = true)
        listener = SolicitarPagamentoListener(pagamentoGateway, eventPublisher)
    }

    @Test
    fun publicaEventoDeLinkPagamentoCriadoComSucesso() {
        val pedido = Pedido(
            id = UUID.randomUUID(),
            total = BigDecimal.TEN,
            clienteNome = "Cliente Teste",
            status = StatusPedido.PEDIDO_CONFIRMADO
        )
        val event = PedidoConfirmadoEvent(pedido = pedido)
        val linkPagamento = "http://link-pagamento.com"

        every { pagamentoGateway.gerarLink(pedido.total!!) } returns linkPagamento

        listener.onEvent(event)

        verify {
            eventPublisher.publish(
                withArg<LinkPagamentoCriadoEvent> {
                    assertEquals(pedido, it.pedido)
                    assertEquals(linkPagamento, it.linkPagamento)
                }
            )
        }
    }

    @Test
    fun lancaExcecaoQuandoTotalDoPedidoEhNulo() {
        val pedido = Pedido(
            id = UUID.randomUUID(),
            total = null,
            clienteNome = "Cliente Teste",
            status = StatusPedido.PEDIDO_CONFIRMADO
        )
        val event = PedidoConfirmadoEvent(pedido = pedido)

        val exception = assertThrows<IllegalArgumentException> {
            listener.onEvent(event)
        }

        assertEquals("O pedido não possui valor total definido.", exception.message)
        verify(exactly = 0) { eventPublisher.publish(any()) }
    }
}