package br.com.felixgilioli.fastfood.application.ports.driver

import java.util.*

interface PagamentoUseCase {

    fun aprovarPagamento(pagamentoId: UUID)

    fun recusarPagamento(pagamentoId: UUID)
}