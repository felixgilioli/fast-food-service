package br.com.felixgilioli.fastfood.infrastructure.driver.api.to.request

import br.com.felixgilioli.fastfood.domain.entities.Cliente

data class CadastrarClienteRequest(
    val nomeCompleto: String,
    val email: String
) {

    fun toEntity() = Cliente(
        nomeCompleto = nomeCompleto,
        email = email
    )
}