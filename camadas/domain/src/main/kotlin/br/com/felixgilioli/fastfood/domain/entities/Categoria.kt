package br.com.felixgilioli.fastfood.domain.entities

import java.util.UUID

data class Categoria(
    val id: UUID? = null,
    val descricao: String
)