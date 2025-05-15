package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm

import br.com.felixgilioli.fastfood.core.entities.Pedido
import br.com.felixgilioli.fastfood.core.entities.StatusPedido
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "pedido")
data class PedidoORM(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    val id: UUID? = null,

    @Column(name = "status", nullable = false, length = 20)
    val status: String,

    @Column(name = "data_inicio", nullable = false)
    val dataInicio: LocalDateTime,

    @Column(name = "data_fim")
    val dataFim: LocalDateTime? = null,

    @Column(name = "cliente_nome", nullable = false, length = 255)
    val clienteNome: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    val cliente: ClienteORM? = null,

    @Column(name = "total", precision = 10, scale = 2)
    val total: BigDecimal? = null
) {
    fun toDomain() = Pedido(
        id = id,
        status = StatusPedido.valueOf(status),
        dataInicio = dataInicio,
        dataFim = dataFim,
        clienteNome = clienteNome,
        total = total
    )

}

fun Pedido.toOrm() = PedidoORM(
    id = this.id,
    status = this.status.name,
    dataInicio = this.dataInicio,
    dataFim = this.dataFim,
    clienteNome = this.clienteNome,
    cliente = this.cliente?.toORM(),
    total = this.total
)
