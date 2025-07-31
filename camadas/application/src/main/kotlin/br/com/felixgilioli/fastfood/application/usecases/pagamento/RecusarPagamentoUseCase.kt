package br.com.felixgilioli.fastfood.application.usecases.pagamento

import br.com.felixgilioli.fastfood.application.events.PagamentoRecusadoEvent
import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.application.events.EventPublisher
import br.com.felixgilioli.fastfood.domain.entities.PagamentoStatus
import java.util.UUID

class RecusarPagamentoUseCase(
    private val pagamentoGateway: PagamentoGateway,
    private val eventPublisher: EventPublisher
) {

    fun execute(pagamentoId: UUID) {
        pagamentoGateway.findById(pagamentoId)
            ?.copyWithNewStatus(PagamentoStatus.PAGAMENTO_REPROVADO)
            ?.let(pagamentoGateway::insert)
            ?.also { eventPublisher.publish(PagamentoRecusadoEvent(it.pedido)) }
            ?: throw IllegalArgumentException("Pagamento não encontrado")
    }
}