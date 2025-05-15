package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "pedido")
data class PedidoIdORM(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    val id: UUID? = null,
)
