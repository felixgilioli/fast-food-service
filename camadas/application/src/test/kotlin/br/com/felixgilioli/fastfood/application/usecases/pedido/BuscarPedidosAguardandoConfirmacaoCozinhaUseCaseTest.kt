package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class BuscarPedidosAguardandoConfirmacaoCozinhaUseCaseTest {

    private lateinit var pedidoGateway: PedidoGateway
    private lateinit var useCase: BuscarPedidosAguardandoConfirmacaoCozinhaUseCase

    @BeforeEach
    fun setUp() {
        pedidoGateway = mockk()
        useCase = BuscarPedidosAguardandoConfirmacaoCozinhaUseCase(pedidoGateway)
    }

    @Test
    fun retornaPedidosAguardandoConfirmacaoCozinha() {
        val pedidos = listOf(
            Pedido(id = UUID.randomUUID(), status = StatusPedido.PAGAMENTO_APROVADO, clienteNome = "Cliente 1"),
            Pedido(id = UUID.randomUUID(), status = StatusPedido.PAGAMENTO_APROVADO, clienteNome = "Cliente 2")
        )
        every { pedidoGateway.findByStatus(StatusPedido.PAGAMENTO_APROVADO) } returns pedidos

        val resultado = useCase.execute()

        assertEquals(2, resultado.size)
        assertEquals(StatusPedido.PAGAMENTO_APROVADO, resultado[0].status)
        verify { pedidoGateway.findByStatus(StatusPedido.PAGAMENTO_APROVADO) }
    }
}