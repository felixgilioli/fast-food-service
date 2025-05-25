package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.entities.Cliente
import br.com.felixgilioli.fastfood.core.exceptions.ClienteAlreadyExistsException
import br.com.felixgilioli.fastfood.core.ports.driven.ClienteRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ClienteUseCaseImplTest {

    private lateinit var clienteRepository: ClienteRepository
    private lateinit var clienteUseCase: ClienteUseCaseImpl

    @BeforeEach
    fun setUp() {
        clienteRepository = mockk()
        clienteUseCase = ClienteUseCaseImpl(clienteRepository)
    }

    @Test
    fun `deve cadastrar cliente quando email nao existe`() {
        val cliente = Cliente(UUID.randomUUID(), "Fulano", "fulano@email.com")
        every { clienteRepository.findByEmail(cliente.email) } returns null
        every { clienteRepository.save(cliente) } returns cliente

        val result = clienteUseCase.cadastrar(cliente)

        assertEquals(cliente, result)
        verify { clienteRepository.findByEmail(cliente.email) }
        verify { clienteRepository.save(cliente) }
    }

    @Test
    fun `deve lancar excecao quando email ja existe`() {
        val cliente = Cliente(UUID.randomUUID(), "Fulano", "fulano@email.com")
        every { clienteRepository.findByEmail(cliente.email) } returns cliente

        assertThrows(ClienteAlreadyExistsException::class.java) {
            clienteUseCase.cadastrar(cliente)
        }

        verify { clienteRepository.findByEmail(cliente.email) }
        verify(exactly = 0) { clienteRepository.save(any()) }
    }

}