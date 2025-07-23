package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.domain.entities.Pagamento
import br.com.felixgilioli.fastfood.domain.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class BuscarPagamentoByPedidoUseCaseTest {

    private lateinit var pagamentoGateway: PagamentoGateway
    private lateinit var useCase: BuscarPagamentoByPedidoUseCase

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
        useCase = BuscarPagamentoByPedidoUseCase(pagamentoGateway)
    }

    @Test
    fun `deve retornar pagamento pelo pedido`() {
        every { pagamentoGateway.findLastByPedidoId(pedidoId) } returns pagamento

        val result = useCase.execute(pedidoId)

        assertEquals(pagamento, result)
        verify { pagamentoGateway.findLastByPedidoId(pedidoId) }
    }
}