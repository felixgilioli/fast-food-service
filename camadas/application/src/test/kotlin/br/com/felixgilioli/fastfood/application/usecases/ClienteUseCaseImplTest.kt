package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.domain.entities.Cliente
import br.com.felixgilioli.fastfood.application.exceptions.ClienteAlreadyExistsException
import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.application.usecases.impl.CadastrarClienteUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ClienteUseCaseImplTest {

    private lateinit var clienteGateway: ClienteGateway
    private lateinit var clienteUseCase: CadastrarClienteUseCaseImpl

    @BeforeEach
    fun setUp() {
        clienteGateway = mockk()
        clienteUseCase = CadastrarClienteUseCaseImpl(clienteGateway)
    }

    @Test
    fun `deve cadastrar cliente quando email nao existe`() {
        val cliente = Cliente(UUID.randomUUID(), "Fulano", "fulano@email.com")
        every { clienteGateway.findByEmail(cliente.email) } returns null
        every { clienteGateway.save(cliente) } returns cliente

        val result = clienteUseCase.execute(cliente)

        assertEquals(cliente, result)
        verify { clienteGateway.findByEmail(cliente.email) }
        verify { clienteGateway.save(cliente) }
    }

    @Test
    fun `deve lancar excecao quando email ja existe`() {
        val cliente = Cliente(UUID.randomUUID(), "Fulano", "fulano@email.com")
        every { clienteGateway.findByEmail(cliente.email) } returns cliente

        assertThrows(ClienteAlreadyExistsException::class.java) {
            clienteUseCase.execute(cliente)
        }

        verify { clienteGateway.findByEmail(cliente.email) }
        verify(exactly = 0) { clienteGateway.save(any()) }
    }

}