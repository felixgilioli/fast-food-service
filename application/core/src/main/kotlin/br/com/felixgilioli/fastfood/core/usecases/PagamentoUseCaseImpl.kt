package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.ports.driven.PagamentoRepository
import br.com.felixgilioli.fastfood.core.ports.driver.PagamentoUseCase
import java.util.*

class PagamentoUseCaseImpl(private val pagamentoRepository: PagamentoRepository) : PagamentoUseCase {

    override fun getPagamentoByPedido(pedidoId: UUID) = pagamentoRepository.findLastByPedidoId(pedidoId)
}