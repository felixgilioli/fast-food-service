package br.com.felixgilioli.fastfood.application.ports.driven

import br.com.felixgilioli.fastfood.domain.entities.Pagamento
import java.util.*

interface PagamentoRepository {

    fun insert(pagamento: Pagamento): Pagamento

    fun findById(id: UUID): Pagamento?

    fun findLastByPedidoId(pedidoId: UUID): Pagamento?
}