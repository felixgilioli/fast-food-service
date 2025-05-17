package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.events.Event
import br.com.felixgilioli.fastfood.core.events.PagamentoAprovadoEvent
import br.com.felixgilioli.fastfood.core.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.core.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.core.ports.driven.PagamentoRepository
import br.com.felixgilioli.fastfood.core.ports.driver.PagamentoUseCase
import java.time.LocalDateTime
import java.util.*

class PagamentoUseCaseImpl(
    private val pagamentoRepository: PagamentoRepository,
    private val eventPublisher: EventPublisher
) : PagamentoUseCase {

    override fun getPagamentoByPedido(pedidoId: UUID) = pagamentoRepository.findLastByPedidoId(pedidoId)

    override fun aprovarPagamento(pagamentoId: UUID) {
        atualizarPagamento(pagamentoId, PagamentoStatus.PAGAMENTO_APROVADO, ::PagamentoAprovadoEvent)
    }

    override fun recusarPagamento(pagamentoId: UUID) {
        atualizarPagamento(pagamentoId, PagamentoStatus.PAGAMENTO_REPROVADO, ::PagamentoRecusadoEvent)
    }

    private fun atualizarPagamento(pagamentoId: UUID, statusPagamento: PagamentoStatus, event: (Pedido) -> Event) {
        pagamentoRepository.findById(pagamentoId)
            ?.copy(id = null, status = statusPagamento, data = LocalDateTime.now())
            ?.let(pagamentoRepository::insert)
            ?.also { eventPublisher.publish(event.invoke(it.pedido)) }
            ?: throw IllegalArgumentException("Pagamento não encontrado")
    }
}