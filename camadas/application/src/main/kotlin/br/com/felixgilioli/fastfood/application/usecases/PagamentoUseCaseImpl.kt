package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.events.Event
import br.com.felixgilioli.fastfood.application.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.application.ports.driver.PagamentoUseCase
import br.com.felixgilioli.fastfood.domain.entities.PagamentoStatus
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import java.util.*

class PagamentoUseCaseImpl(
    private val pagamentoGateway: PagamentoGateway,
    private val eventPublisher: EventPublisher
) : PagamentoUseCase {

    override fun recusarPagamento(pagamentoId: UUID) {
        atualizarPagamento(pagamentoId, PagamentoStatus.PAGAMENTO_REPROVADO, ::PagamentoRecusadoEvent)
    }

    private fun atualizarPagamento(pagamentoId: UUID, statusPagamento: PagamentoStatus, event: (Pedido) -> Event) {
        pagamentoGateway.findById(pagamentoId)
            ?.copyWithNewStatus(statusPagamento)
            ?.let(pagamentoGateway::insert)
            ?.also { eventPublisher.publish(event.invoke(it.pedido)) }
            ?: throw IllegalArgumentException("Pagamento não encontrado")
    }
}