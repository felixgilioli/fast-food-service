package br.com.felixgilioli.fastfood.infrastructure.driven.datasource

import br.com.felixgilioli.fastfood.infrastructure.driven.datasource.orm.repository.PagamentoORMRepository
import br.com.felixgilioli.fastfood.infrastructure.driven.datasource.orm.toOrm
import br.com.felixgilioli.fastfood.domain.entities.Pagamento
import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class PagamentoGatewayImpl(private val pagamentoORMRepository: PagamentoORMRepository) : PagamentoGateway {

    override fun insert(pagamento: Pagamento) =
        pagamentoORMRepository.save(pagamento.toOrm()).toDomain()

    override fun findById(id: UUID) = pagamentoORMRepository.findByIdOrNull(id)?.toDomain()

    override fun findLastByPedidoId(pedidoId: UUID) =
        pagamentoORMRepository.findFirstByPedidoIdOrderByDataDesc(pedidoId)?.toDomain()
}