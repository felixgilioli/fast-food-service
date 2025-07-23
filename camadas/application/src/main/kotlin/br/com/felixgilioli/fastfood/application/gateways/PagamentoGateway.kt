package br.com.felixgilioli.fastfood.application.gateways

import br.com.felixgilioli.fastfood.domain.entities.Pagamento
import java.util.UUID

interface PagamentoGateway {

    fun insert(pagamento: Pagamento): Pagamento

    fun findById(id: UUID): Pagamento?

    fun findLastByPedidoId(pedidoId: UUID): Pagamento?
}