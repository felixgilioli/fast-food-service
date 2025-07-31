package br.com.felixgilioli.fastfood.application.usecases.listener

import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import br.com.felixgilioli.fastfood.application.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class PagamentoRecusadoListenerTest {

    private lateinit var pedidoGateway: PedidoGateway
    private lateinit var listener: PagamentoRecusadoListener

    @BeforeEach
    fun setUp() {
        pedidoGateway = mockk(relaxed = true)
        listener = PagamentoRecusadoListener(pedidoGateway)
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
            pedidoGateway.save(
                withArg {
                    assertEquals(StatusPedido.PAGAMENTO_RECUSADO, it.status)
                    assertEquals(pedido.id, it.id)
                }
            )
        }
    }

}