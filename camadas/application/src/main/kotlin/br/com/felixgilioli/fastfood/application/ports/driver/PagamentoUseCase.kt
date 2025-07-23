package br.com.felixgilioli.fastfood.application.ports.driver

import java.util.*

interface PagamentoUseCase {

    fun recusarPagamento(pagamentoId: UUID)
}