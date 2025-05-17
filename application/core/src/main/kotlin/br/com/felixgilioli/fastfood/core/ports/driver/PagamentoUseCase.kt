package br.com.felixgilioli.fastfood.core.ports.driver

import br.com.felixgilioli.fastfood.core.entities.Pagamento
import java.util.*

interface PagamentoUseCase {

    fun getPagamentoByPedido(pedidoId: UUID): Pagamento?

    fun aprovarPagamento(pagamentoId: UUID)

    fun recusarPagamento(pagamentoId: UUID)
}