package br.com.felixgilioli.fastfood.adapters.driver.api.to.response

import br.com.felixgilioli.fastfood.core.entities.Cliente

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