package br.com.felixgilioli.fastfood.infrastructure.config

import br.com.felixgilioli.fastfood.application.exceptions.ClienteAlreadyExistsException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()

    @Test
    fun `handleClienteAlreadyExists retorna 409 e mensagem da excecao`() {
        val ex = ClienteAlreadyExistsException("Cliente já existe")

        val response = handler.handleClienteAlreadyExists(ex)

        assertNotNull(response)
        assertEquals(HttpStatus.CONFLICT, response.statusCode)
        assertEquals(HttpStatus.CONFLICT.value(), response.body?.status)
    }

    @Test
    fun `handleClienteAlreadyExists retorna 409 e mensagem default quando excecao nao tem mensagem`() {
        val ex = ClienteAlreadyExistsException("")

        val response = handler.handleClienteAlreadyExists(ex)

        assertNotNull(response)
        assertEquals(HttpStatus.CONFLICT, response.statusCode)
        assertEquals(HttpStatus.CONFLICT.value(), response.body?.status)
    }

    @Test
    fun `handleIllegalArgument retorna 400 e mensagem da excecao`() {
        val ex = IllegalArgumentException("Requisição inválida")

        val response = handler.handleIllegalArgument(ex)

        assertNotNull(response)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.body?.status)
        assertEquals("Requisição inválida", response.body?.message)
    }

    @Test
    fun `handleIllegalArgument retorna 400 e mensagem default quando excecao nao tem mensagem`() {
        val ex = IllegalArgumentException()

        val response = handler.handleIllegalArgument(ex)

        assertNotNull(response)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.body?.status)
        assertEquals("Requisição inválida", response.body?.message)
    }

    @Test
    fun `handleAll retorna 500 e mensagem da excecao`() {
        val ex = RuntimeException("Falha inesperada")

        val response = handler.handleAll(ex)

        assertNotNull(response)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.body?.status)
        assertEquals("Falha inesperada", response.body?.message)
    }

    @Test
    fun `handleAll retorna 500 e mensagem default quando excecao nao tem mensagem`() {
        val ex = RuntimeException()

        val response = handler.handleAll(ex)

        assertNotNull(response)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.body?.status)
        assertEquals("Erro interno no servidor", response.body?.message)
    }
}

