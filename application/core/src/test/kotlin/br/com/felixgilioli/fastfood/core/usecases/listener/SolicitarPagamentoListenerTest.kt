package br.com.felixgilioli.fastfood.core.usecases.listener

import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import br.com.felixgilioli.fastfood.core.events.LinkPagamentoCriadoEvent
import br.com.felixgilioli.fastfood.core.events.PedidoConfirmadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.core.ports.driven.GeradorLinkPagamento
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

    private lateinit var geradorLinkPagamento: GeradorLinkPagamento
    private lateinit var eventPublisher: EventPublisher
    private lateinit var listener: SolicitarPagamentoListener

    @BeforeEach
    fun setUp() {
        geradorLinkPagamento = mockk()
        eventPublisher = mockk(relaxed = true)
        listener = SolicitarPagamentoListener(geradorLinkPagamento, eventPublisher)
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

        every { geradorLinkPagamento.gerarLink(pedido.total!!) } returns linkPagamento

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