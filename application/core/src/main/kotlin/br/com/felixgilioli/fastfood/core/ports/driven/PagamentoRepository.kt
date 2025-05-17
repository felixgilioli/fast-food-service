package br.com.felixgilioli.fastfood.core.ports.driven

import br.com.felixgilioli.fastfood.core.entities.Pagamento
import java.util.*

interface PagamentoRepository {

    fun insert(pagamento: Pagamento): Pagamento

    fun findById(id: UUID): Pagamento?

    fun findLastByPedidoId(pedidoId: UUID): Pagamento?
}