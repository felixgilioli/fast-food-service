package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "item_pedido")
data class ItemPedidoORM(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    val pedido: PedidoORM,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    val produto: ProdutoORM,

    @Column(name = "quantidade", nullable = false)
    val quantidade: Int,

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    val precoUnitario: BigDecimal
)