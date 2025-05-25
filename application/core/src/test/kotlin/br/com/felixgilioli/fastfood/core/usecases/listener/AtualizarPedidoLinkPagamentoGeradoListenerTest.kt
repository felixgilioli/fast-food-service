package br.com.felixgilioli.fastfood.core.usecases.listener

import br.com.felixgilioli.fastfood.core.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import br.com.felixgilioli.fastfood.core.events.LinkPagamentoCriadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.PagamentoRepository
import br.com.felixgilioli.fastfood.core.ports.driven.PedidoRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class AtualizarPedidoLinkPagamentoGeradoListenerTest {

    private lateinit var pagamentoRepository: PagamentoRepository
    private lateinit var pedidoRepository: PedidoRepository
    private lateinit var listener: AtualizarPedidoLinkPagamentoGeradoListener

    @BeforeEach
    fun setUp() {
        pagamentoRepository = mockk(relaxed = true)
        pedidoRepository = mockk(relaxed = true)
        listener = AtualizarPedidoLinkPagamentoGeradoListener(pagamentoRepository, pedidoRepository)
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
            pagamentoRepository.insert(
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

        verify(exactly = 0) { pagamentoRepository.insert(any()) }
        verify(exactly = 0) { pedidoRepository.save(any()) }
    }
}