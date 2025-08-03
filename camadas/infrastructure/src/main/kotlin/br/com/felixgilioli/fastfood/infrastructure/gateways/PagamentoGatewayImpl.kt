package br.com.felixgilioli.fastfood.infrastructure.gateways

import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.domain.entities.Pagamento
import br.com.felixgilioli.fastfood.infrastructure.repository.PagamentoORMRepository
import br.com.felixgilioli.fastfood.infrastructure.orm.toOrm
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class PagamentoGatewayImpl(
    private val pagamentoORMRepository: PagamentoORMRepository,
    private val geradorLinkPagamentoMercadoPago: GeradorLinkPagamentoMercadoPago
) : PagamentoGateway {

    override fun insert(pagamento: Pagamento) =
        pagamentoORMRepository.save(pagamento.toOrm()).toDomain()

    override fun findById(id: UUID) = pagamentoORMRepository.findByIdOrNull(id)?.toDomain()

    override fun findLastByPedidoId(pedidoId: UUID) =
        pagamentoORMRepository.findFirstByPedidoIdOrderByDataDesc(pedidoId)?.toDomain()

    override fun gerarLink(valor: BigDecimal) = geradorLinkPagamentoMercadoPago.gerarLink(valor)
}