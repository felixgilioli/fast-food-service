package br.com.felixgilioli.fastfood.infrastructure.driver.api

import br.com.felixgilioli.fastfood.application.usecases.cliente.CadastrarClienteUseCase
import br.com.felixgilioli.fastfood.domain.entities.Cliente
import br.com.felixgilioli.fastfood.infrastructure.web.api.dto.request.CadastrarClienteRequest
import br.com.felixgilioli.fastfood.infrastructure.web.api.ClienteController
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import java.util.*

class ClienteControllerTest {

    private val cadastrarClienteUseCase: CadastrarClienteUseCase = mockk()
    private val clienteController = ClienteController(cadastrarClienteUseCase)

    @Test
    fun cadastrarClienteComSucesso() {
        val request = CadastrarClienteRequest(nomeCompleto = "João", email = "joao@email.com")
        val cliente = Cliente(id = UUID.randomUUID(), nomeCompleto = "João", email = "joao@email.com")
        every { cadastrarClienteUseCase.execute(any()) } returns cliente

        val response = clienteController.cadastrar(request)

        assertNotNull(response)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals("/v1/cliente/${cliente.id}", response.headers.location.toString())
        assertEquals(cliente.id?.toString(), response.body?.clienteId)
        assertEquals(cliente.nomeCompleto, response.body?.nomeCompleto)
        assertEquals(cliente.email, response.body?.email)
    }

    @Test
    fun cadastrarClienteComDadosInvalidos() {
        val request = CadastrarClienteRequest(nomeCompleto = "", email = "email_invalido")
        every { cadastrarClienteUseCase.execute(any()) } throws IllegalArgumentException()

        val exception = assertThrows<IllegalArgumentException> {
            clienteController.cadastrar(request)
        }

        assertEquals("Nome completo não pode estar em branco", exception.message)
    }
}