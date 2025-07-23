package br.com.felixgilioli.fastfood.application.usecases.listener

import br.com.felixgilioli.fastfood.domain.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.application.events.LinkPagamentoCriadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.application.ports.driven.PedidoRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class AtualizarPedidoLinkPagamentoGeradoListenerTest {

    private lateinit var pagamentoGateway: PagamentoGateway
    private lateinit var pedidoRepository: PedidoRepository
    private lateinit var listener: AtualizarPedidoLinkPagamentoGeradoListener

    @BeforeEach
    fun setUp() {
        pagamentoGateway = mockk(relaxed = true)
        pedidoRepository = mockk(relaxed = true)
        listener = AtualizarPedidoLinkPagamentoGeradoListener(pagamentoGateway, pedidoRepository)
    }

    @Test
    fun inserePagamentoComLinkGeradoComSucesso() {
        val pedido = Pedido(
            id = UUID.randomUUID(),
            status = StatusPedido.CRIADO,
            clienteNome = "Cliente Teste",
            total = BigDecimal.TEN
        )
        val event = LinkPagamentoCriadoEvent(
            pedido = pedido,
            linkPagamento = "http://link-pagamento.com"
        )

        listener.onEvent(event)

        verify {
            pagamentoGateway.insert(
                withArg {
                    assertEquals(pedido, it.pedido)
                    assertEquals(BigDecimal.TEN, it.valor)
                    assertEquals(PagamentoStatus.LINK_PAGAMENTO_GERADO, it.status)
                    assertEquals("http://link-pagamento.com", it.link)
                }
            )
        }
    }

    @Test
    fun atualizaStatusDoPedidoParaPagamentoSolicitado() {
        val pedido = Pedido(
            id = UUID.randomUUID(),
            status = StatusPedido.CRIADO,
            clienteNome = "Cliente Teste",
            total = BigDecimal.TEN
        )
        val event = LinkPagamentoCriadoEvent(
            pedido = pedido,
            linkPagamento = "http://link-pagamento.com"
        )

        listener.onEvent(event)

        verify {
            pedidoRepository.save(
                withArg {
                    assertEquals(StatusPedido.PAGAMENTO_SOLICITADO, it.status)
                    assertEquals(pedido.id, it.id)
                }
            )
        }
    }

    @Test
    fun naoInserePagamentoQuandoTotalDoPedidoEhNulo() {
        val pedido = Pedido(
            id = UUID.randomUUID(),
            status = StatusPedido.CRIADO,
            clienteNome = "Cliente Teste",
            total = null
        )
        val event = LinkPagamentoCriadoEvent(
            pedido = pedido,
            linkPagamento = "http://link-pagamento.com"
        )

        assertThrows<NullPointerException> {
            listener.onEvent(event)
        }

        verify(exactly = 0) { pagamentoGateway.insert(any()) }
        verify(exactly = 0) { pedidoRepository.save(any()) }
    }
}