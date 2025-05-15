package br.com.felixgilioli.fastfood.core.entities

import java.util.*

data class Cliente(
    val id: UUID? = null,
    val nomeCompleto: String,
    val email: String
) {
    init {
        require(nomeCompleto.isNotBlank()) { "Nome completo não pode estar em branco" }
        require(email.isNotBlank()) { "Email não pode estar em branco" }
    }

}