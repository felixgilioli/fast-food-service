package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm

import br.com.felixgilioli.fastfood.core.entities.Categoria
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "categoria")
data class CategoriaORM(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    val id: UUID? = null,

    @Column(name = "descricao", nullable = false, length = 150)
    val descricao: String
) {
    fun toDomain() = Categoria(
        id = id,
        descricao = descricao
    )
}