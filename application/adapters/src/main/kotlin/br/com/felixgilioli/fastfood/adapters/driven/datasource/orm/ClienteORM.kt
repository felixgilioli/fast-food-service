package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm

import br.com.felixgilioli.fastfood.core.entities.Cliente
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "cliente")
data class ClienteORM(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    val id: UUID? = null,

    @Column(name = "nome_completo", nullable = false)
    val nomeCompleto: String,

    @Column(name = "email", nullable = false)
    val email: String
) {
    fun toDomain() = Cliente(
        id = this.id,
        nomeCompleto = this.nomeCompleto,
        email = email
    )
}

fun Cliente.toORM() = ClienteORM(
    id = this.id,
    nomeCompleto = this.nomeCompleto,
    email = this.email
)