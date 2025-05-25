package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.core.entities.Pagamento
import br.com.felixgilioli.fastfood.core.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import br.com.felixgilioli.fastfood.core.ports.driver.PagamentoUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.util.*

class PagamentoControllerTest {

    private val pagamentoUseCase: PagamentoUseCase = mockk()
    private val pagamentoController = PagamentoController(pagamentoUseCase)

    @Test
    fun retornaPagamentoQuandoPedidoIdValido() {
        val pedidoId = UUID.randomUUID().toString()
        val pagamento = Pagamento(
            id = UUID.randomUUID(),
            pedido = Pedido(
                id = UUID.fromString(pedidoId),
                status = StatusPedido.PAGAMENTO_APROVADO,
                clienteNome = "Teste"
            ),
            valor = BigDecimal(100.0),
            status = PagamentoStatus.LINK_PAGAMENTO_GERADO,
            link = "http://link.com"
        )
        every { pagamentoUseCase.getPagamentoByPedido(UUID.fromString(pedidoId)) } returns pagamento

        val response = pagamentoController.getPagamento(pedidoId)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(pagamento.id?.toString(), response.body?.pagamentoId)
    }

    @Test
    fun retornaNotFoundQuandoPedidoIdNaoExiste() {
        val pedidoId = UUID.randomUUID().toString()
        every { pagamentoUseCase.getPagamentoByPedido(UUID.fromString(pedidoId)) } returns null

        val response = pagamentoController.getPagamento(pedidoId)

        assertNotNull(response)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun aprovaPagamentoComSucesso() {
        val pagamentoId = UUID.randomUUID().toString()
        every { pagamentoUseCase.aprovarPagamento(UUID.fromString(pagamentoId)) } returns Unit

        val response = pagamentoController.aprovarPagamento(pagamentoId)

        assertNotNull(response)
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun recusaPagamentoComSucesso() {
        val pagamentoId = UUID.randomUUID().toString()
        every { pagamentoUseCase.recusarPagamento(UUID.fromString(pagamentoId)) } returns Unit

        val response = pagamentoController.recusarPagamento(pagamentoId)

        assertNotNull(response)
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
}