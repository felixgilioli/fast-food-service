package br.com.felixgilioli.fastfood.application.usecases.pedido

import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido.*

class ListarPedidosUseCase(
    private val pedidoGateway: PedidoGateway
) {

    companion object {
        val ETAPAS_ANDAMENTO_PEDIDO = listOf(
            PAGAMENTO_APROVADO,
            EM_PREPARACAO,
            PRONTO
        )
    }

    fun execute(): List<Pedido> = pedidoGateway.findByStatusIn(ETAPAS_ANDAMENTO_PEDIDO)
        .sortedWith(
            compareBy<Pedido> { ETAPAS_ANDAMENTO_PEDIDO.indexOf(it.status) }
                .thenBy { it.dataInicio }
        )
}