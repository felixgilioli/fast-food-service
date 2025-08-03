package br.com.felixgilioli.fastfood.infrastructure.driven.datasource

import br.com.felixgilioli.fastfood.domain.entities.Cliente
import br.com.felixgilioli.fastfood.infrastructure.gateways.ClienteGatewayImpl
import br.com.felixgilioli.fastfood.infrastructure.orm.ClienteORM
import br.com.felixgilioli.fastfood.infrastructure.repository.ClienteORMRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.*

class ClienteDataSourceTest {

    private val clienteORMRepository: ClienteORMRepository = mockk()
    private val clienteGatewayImpl = ClienteGatewayImpl(clienteORMRepository)

    @Test
    fun retornaClienteQuandoEmailExiste() {
        val email = "cliente@teste.com"
        val clienteORM = ClienteORM(UUID.randomUUID(), "Cliente Teste", email)
        every { clienteORMRepository.findByEmail(email) } returns clienteORM

        val cliente = clienteGatewayImpl.findByEmail(email)

        assertEquals(clienteORM.id, cliente?.id)
        assertEquals(clienteORM.nomeCompleto, cliente?.nomeCompleto)
        assertEquals(clienteORM.email, cliente?.email)
    }

    @Test
    fun retornaNullQuandoEmailNaoExiste() {
        val email = "naoexiste@teste.com"
        every { clienteORMRepository.findByEmail(email) } returns null

        val cliente = clienteGatewayImpl.findByEmail(email)

        assertNull(cliente)
    }

    @Test
    fun salvaClienteComSucesso() {
        val cliente = Cliente(UUID.randomUUID(), "Cliente Teste", "cliente@teste.com")
        val clienteORM = ClienteORM(cliente.id, cliente.nomeCompleto, cliente.email)
        every { clienteORMRepository.save(any()) } returns clienteORM

        val clienteSalvo = clienteGatewayImpl.save(cliente)

        assertEquals(cliente.id, clienteSalvo.id)
        assertEquals(cliente.nomeCompleto, clienteSalvo.nomeCompleto)
        assertEquals(cliente.email, clienteSalvo.email)
    }
}