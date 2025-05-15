package br.com.felixgilioli.fastfood.adapters.driver.api.to.request

import br.com.felixgilioli.fastfood.core.entities.Cliente

data class CadastrarClienteRequest(
    val nomeCompleto: String,
    val email: String
) {

    fun toEntity() = Cliente(
        nomeCompleto = nomeCompleto,
        email = email
    )
}