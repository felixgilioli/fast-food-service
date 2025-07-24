package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class PedidoUseCaseImplTest {

    private lateinit var pedidoGateway: PedidoGateway
    private lateinit var pedidoUseCase: PedidoUseCaseImpl

    @BeforeEach
    fun setUp() {
        pedidoGateway = mockk()
        pedidoUseCase = PedidoUseCaseImpl(pedidoGateway)
    }

    @Test
    fun retornaPedidosAguardandoConfirmacaoCozinha() {
        val pedidos = listOf(
            Pedido(id = UUID.randomUUID(), status = StatusPedido.PAGAMENTO_APROVADO, clienteNome = "Cliente 1"),
            Pedido(id = UUID.randomUUID(), status = StatusPedido.PAGAMENTO_APROVADO, clienteNome = "Cliente 2")
        )
        every { pedidoGateway.findByStatus(StatusPedido.PAGAMENTO_APROVADO) } returns pedidos

        val resultado = pedidoUseCase.findPedidosAguardandoConfirmacaoCozinha()

        assertEquals(2, resultado.size)
        assertEquals(StatusPedido.PAGAMENTO_APROVADO, resultado[0].status)
        verify { pedidoGateway.findByStatus(StatusPedido.PAGAMENTO_APROVADO) }
    }
}