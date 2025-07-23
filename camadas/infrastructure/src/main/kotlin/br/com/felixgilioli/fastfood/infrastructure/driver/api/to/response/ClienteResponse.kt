package br.com.felixgilioli.fastfood.infrastructure.driver.api.to.response

import br.com.felixgilioli.fastfood.domain.entities.Cliente

data class ClienteResponse(
    val clienteId: String,
    val nomeCompleto: String,
    val email: String
)

fun Cliente.toResponse() = ClienteResponse(
    clienteId = this.id.toString(),
    nomeCompleto = this.nomeCompleto,
    email = this.email
)