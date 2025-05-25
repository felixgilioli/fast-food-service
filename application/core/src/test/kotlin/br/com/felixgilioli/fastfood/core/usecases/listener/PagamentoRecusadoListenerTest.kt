package br.com.felixgilioli.fastfood.core.usecases.listener

import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import br.com.felixgilioli.fastfood.core.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.PedidoRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class PagamentoRecusadoListenerTest {

    private lateinit var pedidoRepository: PedidoRepository
    private lateinit var listener: PagamentoRecusadoListener

    @BeforeEach
    fun setUp() {
        pedidoRepository = mockk(relaxed = true)
        listener = PagamentoRecusadoListener(pedidoRepository)
    }

    @Test
    fun atualizaStatusDoPedidoParaPagamentoRecusado() {
        val pedido = Pedido(
            id = UUID.randomUUID(),
            status = StatusPedido.PAGAMENTO_SOLICITADO,
            clienteNome = "Cliente Teste",
            total = BigDecimal.TEN
        )
        val event = PagamentoRecusadoEvent(pedido = pedido)

        listener.onEvent(event)

        verify {
            pedidoRepository.save(
                withArg {
                    assertEquals(StatusPedido.PAGAMENTO_RECUSADO, it.status)
                    assertEquals(pedido.id, it.id)
                }
            )
        }
    }

}