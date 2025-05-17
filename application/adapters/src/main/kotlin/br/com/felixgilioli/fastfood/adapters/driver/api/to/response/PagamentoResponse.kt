package br.com.felixgilioli.fastfood.adapters.driver.api.to.response

import br.com.felixgilioli.fastfood.core.entities.Pagamento
import java.math.BigDecimal
import java.time.LocalDateTime

data class PagamentoResponse(
    val pagamentoId: String,
    val pedidoId: String,
    val data: LocalDateTime,
    val valor: BigDecimal,
    val status: String,
    val link: String
)

fun Pagamento.toResponse() = PagamentoResponse(
    pagamentoId = this.id.toString(),
    pedidoId = this.pedido.id.toString(),
    data = this.data,
    valor = this.valor,
    status = this.status.name,
    link = this.link
)