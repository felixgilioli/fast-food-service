package br.com.felixgilioli.fastfood.application.usecases.pagamento

import br.com.felixgilioli.fastfood.application.events.PagamentoAprovadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.domain.entities.PagamentoStatus
import java.util.UUID

class AprovarPagamentoUseCase(
    private val pagamentoGateway: PagamentoGateway,
    private val eventPublisher: EventPublisher
) {

    fun execute(pagamentoId: UUID) {
        pagamentoGateway.findById(pagamentoId)
            ?.copyWithNewStatus(PagamentoStatus.PAGAMENTO_APROVADO)
            ?.let(pagamentoGateway::insert)
            ?.also { eventPublisher.publish(PagamentoAprovadoEvent(it.pedido)) }
            ?: throw IllegalArgumentException("Pagamento não encontrado")
    }
}