package br.com.felixgilioli.fastfood.adapters.driven.datasource

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.ClienteORM
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository.ClienteORMRepository
import br.com.felixgilioli.fastfood.core.entities.Cliente
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.*

class ClienteDataSourceTest {

    private val clienteORMRepository: ClienteORMRepository = mockk()
    private val clienteDataSource = ClienteDataSource(clienteORMRepository)

    @Test
    fun retornaClienteQuandoEmailExiste() {
        val email = "cliente@teste.com"
        val clienteORM = ClienteORM(UUID.randomUUID(), "Cliente Teste", email)
        every { clienteORMRepository.findByEmail(email) } returns clienteORM

        val cliente = clienteDataSource.findByEmail(email)

        assertEquals(clienteORM.id, cliente?.id)
        assertEquals(clienteORM.nomeCompleto, cliente?.nomeCompleto)
        assertEquals(clienteORM.email, cliente?.email)
    }

    @Test
    fun retornaNullQuandoEmailNaoExiste() {
        val email = "naoexiste@teste.com"
        every { clienteORMRepository.findByEmail(email) } returns null

        val cliente = clienteDataSource.findByEmail(email)

        assertNull(cliente)
    }

    @Test
    fun salvaClienteComSucesso() {
        val cliente = Cliente(UUID.randomUUID(), "Cliente Teste", "cliente@teste.com")
        val clienteORM = ClienteORM(cliente.id, cliente.nomeCompleto, cliente.email)
        every { clienteORMRepository.save(any()) } returns clienteORM

        val clienteSalvo = clienteDataSource.save(cliente)

        assertEquals(cliente.id, clienteSalvo.id)
        assertEquals(cliente.nomeCompleto, clienteSalvo.nomeCompleto)
        assertEquals(cliente.email, clienteSalvo.email)
    }
}