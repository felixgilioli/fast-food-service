package br.com.felixgilioli.fastfood.adapters.driven.datasource

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.PedidoORM
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository.PedidoORMRepository
import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import java.util.*

class PedidoDataSourceTest {

    private val pedidoORMRepository: PedidoORMRepository = mockk()
    private val pedidoDataSource = PedidoDataSource(pedidoORMRepository)

    @Test
    fun retornaPedidoQuandoIdExiste() {
        val pedidoId = UUID.randomUUID()
        val pedidoORM = PedidoORM(
            id = pedidoId,
            status = StatusPedido.CRIADO,
            dataInicio = LocalDateTime.now(),
            clienteNome = "teste"
        )
        every { pedidoORMRepository.findByIdOrNull(pedidoId) } returns pedidoORM

        val resultado = pedidoDataSource.findById(pedidoId)

        assertEquals(pedidoORM.id, resultado?.id)
        assertEquals(pedidoORM.status, resultado?.status)
    }

    @Test
    fun retornaNullQuandoIdNaoExiste() {
        val pedidoId = UUID.randomUUID()
        every { pedidoORMRepository.findByIdOrNull(pedidoId) } returns null

        val resultado = pedidoDataSource.findById(pedidoId)

        assertNull(resultado)
    }

    @Test
    fun salvaPedidoComSucesso() {
        val pedido = Pedido(
            id = UUID.randomUUID(),
            status = StatusPedido.PAGAMENTO_APROVADO,
            clienteNome = "Teste"
        )
        val pedidoORM = PedidoORM(
            id = pedido.id,
            status = pedido.status,
            dataInicio = LocalDateTime.now(),
            clienteNome = "teste"
        )
        every { pedidoORMRepository.save(any()) } returns pedidoORM

        val resultado = pedidoDataSource.save(pedido)

        assertEquals(pedido.id, resultado.id)
        assertEquals(pedido.status, resultado.status)
    }

    @Test
    fun retornaPedidosPorStatusComSucesso() {
        val status = StatusPedido.PEDIDO_CONFIRMADO
        val pedidosORM = listOf(
            PedidoORM(
                id = UUID.randomUUID(),
                status = status,
                dataInicio = LocalDateTime.now(),
                clienteNome = "teste"
            ),
            PedidoORM(
                id = UUID.randomUUID(),
                status = status,
                dataInicio = LocalDateTime.now(),
                clienteNome = "teste 2"
            )
        )
        every { pedidoORMRepository.findByStatus(status) } returns pedidosORM

        val resultado = pedidoDataSource.findByStatus(status)

        assertEquals(2, resultado.size)
        assertEquals(status, resultado[0].status)
        assertEquals(status, resultado[1].status)
    }

    @Test
    fun retornaListaVaziaQuandoNaoHaPedidosComStatus() {
        val status = StatusPedido.FINALIZADO
        every { pedidoORMRepository.findByStatus(status) } returns emptyList()

        val resultado = pedidoDataSource.findByStatus(status)

        assertEquals(0, resultado.size)
    }
}