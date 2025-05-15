package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm

import br.com.felixgilioli.fastfood.core.entities.PedidoItem
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "pedido_item")
data class PedidoItemORM(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    val pedido: PedidoIdORM,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    val produto: ProdutoORM,

    @Column(name = "quantidade", nullable = false)
    val quantidade: Int,

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    val precoUnitario: BigDecimal
) {
    fun toDomain() = PedidoItem(
        id = id,
        pedidoId = pedido.id!!,
        produto = produto.toDomain(),
        quantidade = quantidade,
        precoUnitario = precoUnitario
    )
}

fun PedidoItem.toOrm() = PedidoItemORM(
    id = this.id,
    pedido = PedidoIdORM(this.pedidoId),
    produto = this.produto.toORM(),
    quantidade = this.quantidade,
    precoUnitario = this.precoUnitario
)