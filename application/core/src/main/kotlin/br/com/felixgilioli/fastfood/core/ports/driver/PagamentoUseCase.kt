package br.com.felixgilioli.fastfood.core.ports.driver

import br.com.felixgilioli.fastfood.core.entities.Pagamento
import java.util.UUID

interface PagamentoUseCase {

    fun getPagamentoByPedido(pedidoId: UUID): Pagamento?
}