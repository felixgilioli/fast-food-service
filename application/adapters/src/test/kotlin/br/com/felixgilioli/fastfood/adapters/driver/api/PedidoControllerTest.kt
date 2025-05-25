package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.ConfirmarPedidoItemRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.ConfirmarPedidoRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.NovoPedidoRequest
import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import br.com.felixgilioli.fastfood.core.ports.driver.PedidoUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

class PedidoControllerTest {

    private val pedidoUseCase: PedidoUseCase = mockk()
    private val pedidoController = PedidoController(pedidoUseCase)

    @Test
    fun criaNovoPedidoComSucesso() {
        val request = NovoPedidoRequest(clienteEmail = "fulano@email.com")
        val pedido = Pedido(id = UUID.randomUUID(), clienteNome = request.clienteEmail!!, status = StatusPedido.CRIADO)
        every { pedidoUseCase.novoPedido(any()) } returns pedido

        val response = pedidoController.novoPedido(request)

        assertNotNull(response)
        assertEquals(pedido.id, response.pedidoId)
        assertEquals(pedido.clienteNome, response.clienteNome)
    }

    @Test
    fun retornaNotFoundQuandoPedidoNaoExiste() {
        val pedidoId = UUID.randomUUID().toString()
        every { pedidoUseCase.findById(UUID.fromString(pedidoId)) } returns null

        val response = pedidoController.acompanharPedido(pedidoId)

        assertNotNull(response)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun confirmaPedidoComSucesso() {
        val request = ConfirmarPedidoRequest(
            pedidoId = UUID.randomUUID().toString(), itens = listOf(
                ConfirmarPedidoItemRequest(produtoId = UUID.randomUUID().toString(), quantidade = 2)
            )
        )
        val pedido =
            Pedido(
                id = UUID.fromString(request.pedidoId),
                clienteNome = "joaozinho",
                status = StatusPedido.PEDIDO_CONFIRMADO
            )
        every { pedidoUseCase.confirmarPedido(any()) } returns pedido

        val response = pedidoController.confirmarPedido(request)

        assertNotNull(response)
        assertEquals(pedido.id, response.pedidoId)
        assertEquals(pedido.clienteNome, response.clienteNome)
    }

    @Test
    fun retornaPedidosAguardandoConfirmacaoCozinha() {
        val pedidos = listOf(
            Pedido(
                id = UUID.randomUUID(),
                clienteNome = "joaozinho",
                status = StatusPedido.PEDIDO_CONFIRMADO
            ),
            Pedido(
                id = UUID.randomUUID(),
                clienteNome = "matheuzinho",
                status = StatusPedido.PEDIDO_CONFIRMADO
            )
        )
        every { pedidoUseCase.findPedidosAguardandoConfirmacaoCozinha() } returns pedidos

        val response = pedidoController.findPedidosAguardandoConfirmacaoCozinha()

        assertNotNull(response)
        assertEquals(2, response.size)
        assertEquals(pedidos[0].id, response[0].pedidoId)
        assertEquals(pedidos[1].id, response[1].pedidoId)
    }

    @Test
    fun confirmaPedidoCozinhaComSucesso() {
        val pedidoId = UUID.randomUUID().toString()
        val pedido = Pedido(
            id = UUID.fromString(pedidoId),
            clienteNome = UUID.randomUUID().toString(),
            status = StatusPedido.EM_PREPARACAO
        )
        every { pedidoUseCase.confirmarPedidoCozinha(UUID.fromString(pedidoId)) } returns pedido

        val response = pedidoController.confirmarPedidoCozinha(pedidoId)

        assertNotNull(response)
        assertEquals(pedido.id, response.pedidoId)
        assertEquals(pedido.clienteNome, response.clienteNome)
    }
}