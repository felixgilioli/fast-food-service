package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm

import br.com.felixgilioli.fastfood.core.entities.Pagamento
import br.com.felixgilioli.fastfood.core.entities.PagamentoStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "pagamento")
data class PagamentoORM(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    val pedido: PedidoORM,

    @Column(nullable = false)
    val valor: BigDecimal,

    @Column(nullable = false)
    val data: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    val status: PagamentoStatus,

    @Column(nullable = false, length = 255)
    val link: String
) {
    fun toDomain() = Pagamento(
        id = this.id,
        pedido = this.pedido.toDomain(),
        valor = this.valor,
        data = this.data,
        status = this.status,
        link = this.link
    )

}

fun Pagamento.toOrm() = PagamentoORM(
    id = this.id,
    pedido = this.pedido.toOrm(),
    valor = this.valor,
    data = this.data,
    status = this.status,
    link = this.link
)
