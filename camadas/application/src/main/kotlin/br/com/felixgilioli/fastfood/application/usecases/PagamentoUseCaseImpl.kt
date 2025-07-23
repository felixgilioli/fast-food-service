package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.domain.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.application.events.Event
import br.com.felixgilioli.fastfood.application.events.PagamentoAprovadoEvent
import br.com.felixgilioli.fastfood.application.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.application.ports.driven.PagamentoRepository
import br.com.felixgilioli.fastfood.application.ports.driver.PagamentoUseCase
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
            ?.copyWithNewStatus(statusPagamento)
            ?.let(pagamentoRepository::insert)
            ?.also { eventPublisher.publish(event.invoke(it.pedido)) }
            ?: throw IllegalArgumentException("Pagamento não encontrado")
    }
}