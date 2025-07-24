package br.com.felixgilioli.fastfood.infrastructure.driver.api

import br.com.felixgilioli.fastfood.application.usecases.pagamento.AprovarPagamentoUseCase
import br.com.felixgilioli.fastfood.application.usecases.pagamento.BuscarPagamentoByPedidoUseCase
import br.com.felixgilioli.fastfood.application.usecases.pagamento.RecusarPagamentoUseCase
import br.com.felixgilioli.fastfood.domain.entities.Pagamento
import br.com.felixgilioli.fastfood.domain.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.util.*

class PagamentoControllerTest {

    private val buscarPagamentoByPedidoUseCase: BuscarPagamentoByPedidoUseCase = mockk()
    private val aprovarPagamentoUseCase: AprovarPagamentoUseCase = mockk()
    private val recusarPagamentoUseCase: RecusarPagamentoUseCase = mockk()
    private val pagamentoController =
        PagamentoController(buscarPagamentoByPedidoUseCase, aprovarPagamentoUseCase, recusarPagamentoUseCase)

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
        every { buscarPagamentoByPedidoUseCase.execute(UUID.fromString(pedidoId)) } returns pagamento

        val response = pagamentoController.getPagamento(pedidoId)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(pagamento.id?.toString(), response.body?.pagamentoId)
    }

    @Test
    fun retornaNotFoundQuandoPedidoIdNaoExiste() {
        val pedidoId = UUID.randomUUID().toString()
        every { buscarPagamentoByPedidoUseCase.execute(UUID.fromString(pedidoId)) } returns null

        val response = pagamentoController.getPagamento(pedidoId)

        assertNotNull(response)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun aprovaPagamentoComSucesso() {
        val pagamentoId = UUID.randomUUID().toString()
        every { aprovarPagamentoUseCase.execute(UUID.fromString(pagamentoId)) } returns Unit

        val response = pagamentoController.aprovarPagamento(pagamentoId)

        assertNotNull(response)
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun recusaPagamentoComSucesso() {
        val pagamentoId = UUID.randomUUID().toString()
        every { recusarPagamentoUseCase.execute(UUID.fromString(pagamentoId)) } returns Unit

        val response = pagamentoController.recusarPagamento(pagamentoId)

        assertNotNull(response)
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
}