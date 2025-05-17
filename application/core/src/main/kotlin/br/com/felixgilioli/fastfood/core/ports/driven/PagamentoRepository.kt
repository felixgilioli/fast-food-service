package br.com.felixgilioli.fastfood.core.ports.driven

import br.com.felixgilioli.fastfood.core.entities.Pagamento

interface PagamentoRepository {

    fun insert(pagamento: Pagamento): Pagamento
}