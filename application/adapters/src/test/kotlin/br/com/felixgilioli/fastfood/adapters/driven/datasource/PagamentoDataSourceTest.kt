package br.com.felixgilioli.fastfood.adapters.driven.datasource

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.PagamentoORM
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.PedidoORM
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository.PagamentoORMRepository
import br.com.felixgilioli.fastfood.core.entities.Pagamento
import br.com.felixgilioli.fastfood.core.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class PagamentoDataSourceTest {

    private val pagamentoORMRepository: PagamentoORMRepository = mockk()
    private val pagamentoDataSource = PagamentoDataSource(pagamentoORMRepository)

    @Test
    fun inserePagamentoComSucesso() {
        val pagamento = Pagamento(
            id = UUID.randomUUID(),
            pedido = Pedido(
                id = UUID.randomUUID(),
                status = StatusPedido.PAGAMENTO_APROVADO,
                clienteNome = "Teste"
            ),
            valor = BigDecimal(100.0),
            status = PagamentoStatus.LINK_PAGAMENTO_GERADO,
            link = "http://link.com"
        )
        val pagamentoORM = PagamentoORM(
            pagamento.id,
            PedidoORM(
                id = pagamento.pedido.id,
                status = StatusPedido.CRIADO,
                dataInicio = LocalDateTime.now(),
                clienteNome = "teste"
            ),
            valor = pagamento.valor,
            status = PagamentoStatus.PAGAMENTO_APROVADO,
            link = "http://link.com"
        )
        every { pagamentoORMRepository.save(any()) } returns pagamentoORM

        val resultado = pagamentoDataSource.insert(pagamento)

        assertEquals(pagamento.id, resultado.id)
        assertEquals(pagamento.pedido.id, resultado.pedido.id)
        assertEquals(pagamento.valor, resultado.valor)
    }

    @Test
    fun retornaPagamentoQuandoIdExiste() {
        val pagamentoId = UUID.randomUUID()
        val pagamentoORM = PagamentoORM(
            pagamentoId,
            PedidoORM(
                id = UUID.randomUUID(),
                status = StatusPedido.CRIADO,
                dataInicio = LocalDateTime.now(),
                clienteNome = "teste"
            ),
            valor = BigDecimal.TEN,
            status = PagamentoStatus.PAGAMENTO_APROVADO,
            link = "http://link.com"
        )
        every { pagamentoORMRepository.findByIdOrNull(pagamentoId) } returns pagamentoORM

        val resultado = pagamentoDataSource.findById(pagamentoId)

        assertEquals(pagamentoORM.id, resultado?.id)
        assertEquals(pagamentoORM.pedido.id, resultado?.pedido?.id)
        assertEquals(pagamentoORM.valor, resultado?.valor)
    }

    @Test
    fun retornaNullQuandoIdNaoExiste() {
        val pagamentoId = UUID.randomUUID()
        every { pagamentoORMRepository.findByIdOrNull(pagamentoId) } returns null

        val resultado = pagamentoDataSource.findById(pagamentoId)

        assertNull(resultado)
    }

    @Test
    fun retornaUltimoPagamentoPorPedidoIdComSucesso() {
        val pedidoId = UUID.randomUUID()
        val pagamentoORM = PagamentoORM(
            UUID.randomUUID(),
            PedidoORM(
                id = pedidoId,
                status = StatusPedido.CRIADO,
                dataInicio = LocalDateTime.now(),
                clienteNome = "teste"
            ),
            valor = BigDecimal.TEN,
            status = PagamentoStatus.PAGAMENTO_APROVADO,
            link = "http://link.com"
        )
        every { pagamentoORMRepository.findFirstByPedidoIdOrderByDataDesc(pedidoId) } returns pagamentoORM

        val resultado = pagamentoDataSource.findLastByPedidoId(pedidoId)

        assertEquals(pagamentoORM.id, resultado?.id)
        assertEquals(pagamentoORM.pedido.id, resultado?.pedido?.id)
        assertEquals(pagamentoORM.valor, resultado?.valor)
    }

    @Test
    fun retornaNullQuandoNaoHaPagamentosParaPedidoId() {
        val pedidoId = UUID.randomUUID()
        every { pagamentoORMRepository.findFirstByPedidoIdOrderByDataDesc(pedidoId) } returns null

        val resultado = pagamentoDataSource.findLastByPedidoId(pedidoId)

        assertNull(resultado)
    }
}