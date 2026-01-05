package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.domain.entities.Cliente
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class NovoPedidoUseCaseTest {
    private lateinit var clienteGateway: ClienteGateway
    private lateinit var pedidoGateway: PedidoGateway
    private lateinit var useCase: NovoPedidoUseCase

    @BeforeEach
    fun setUp() {
        clienteGateway = mockk()
        pedidoGateway = mockk()
        useCase = NovoPedidoUseCase(clienteGateway, pedidoGateway)
    }

    @Test
    fun criaNovoPedidoComClienteExistente() {
        val command = NovoPedidoCommand(clienteEmail = "cliente@email.com", clienteCPF = null)
        val cliente = Cliente(UUID.randomUUID(), "Cliente Teste", "cliente@email.com")
        every { clienteGateway.findByEmail(command.clienteEmail!!) } returns cliente
        every { pedidoGateway.save(any()) } answers { firstArg() }

        val resultado = useCase.execute(command)

        assertEquals(cliente.nomeCompleto, resultado.clienteNome)
        assertEquals(StatusPedido.CRIADO, resultado.status)
        verify { clienteGateway.findByEmail(command.clienteEmail!!) }
        verify { pedidoGateway.save(any()) }
    }

    @Test
    fun lancaExcecaoQuandoClienteNaoEncontrado() {
        val command = NovoPedidoCommand(clienteEmail = "inexistente@email.com", clienteCPF = null)
        every { clienteGateway.findByEmail(command.clienteEmail!!) } returns null

        val excecao = assertThrows(IllegalArgumentException::class.java) {
            useCase.execute(command)
        }

        assertEquals("Cliente não encontrado", excecao.message)
        verify { clienteGateway.findByEmail(command.clienteEmail!!) }
    }
}