package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido

class BuscarPedidosAguardandoConfirmacaoCozinhaUseCase(
    private val pedidoGateway: PedidoGateway
) {

    fun execute() = pedidoGateway.findByStatus(StatusPedido.PAGAMENTO_APROVADO)
        .sortedBy { it.dataInicio }
}