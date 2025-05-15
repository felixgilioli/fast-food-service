package br.com.felixgilioli.fastfood.adapters.driver.api.to.response

import br.com.felixgilioli.fastfood.core.entities.Cliente

data class ClienteResponse(
    val id: String,
    val nomeCompleto: String,
    val email: String
)

fun Cliente.toResponse() = ClienteResponse(
    id = this.id.toString(),
    nomeCompleto = this.nomeCompleto,
    email = this.email
)