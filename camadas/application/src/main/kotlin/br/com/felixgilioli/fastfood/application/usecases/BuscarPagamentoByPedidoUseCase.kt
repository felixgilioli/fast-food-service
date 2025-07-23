package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.domain.entities.Pagamento
import java.util.UUID

class BuscarPagamentoByPedidoUseCase(private val pagamentoGateway: PagamentoGateway) {

    fun execute(pedidoId: UUID): Pagamento? = pagamentoGateway.findLastByPedidoId(pedidoId)
}