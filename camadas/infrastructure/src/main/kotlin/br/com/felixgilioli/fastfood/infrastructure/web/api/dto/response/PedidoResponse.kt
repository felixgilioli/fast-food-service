package br.com.felixgilioli.fastfood.infrastructure.web.api.dto.response

import br.com.felixgilioli.fastfood.domain.entities.Cliente
import br.com.felixgilioli.fastfood.domain.entities.Pedido
import br.com.felixgilioli.fastfood.domain.entities.StatusPedido
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class PedidoResponse(
    val pedidoId: UUID? = null,
    val status: StatusPedido,
    val dataInicio: LocalDateTime,
    val dataFim: LocalDateTime?,
    val clienteNome: String,
    val cliente: Cliente?,
    val total: BigDecimal?
)

fun Pedido.toResponse() = PedidoResponse(
    pedidoId = id,
    status = status,
    dataInicio = dataInicio,
    dataFim = dataFim,
    clienteNome = clienteNome,
    cliente = cliente,
    total = total
)
