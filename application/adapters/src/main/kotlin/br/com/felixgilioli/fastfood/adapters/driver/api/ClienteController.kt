package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.CadastrarClienteRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.toResponse
import br.com.felixgilioli.fastfood.core.ports.driver.ClienteUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/v1/cliente")
@Tag(name = "Cliente API", description = "Cadastro de clientes")
class ClienteController(private val clienteUseCase: ClienteUseCase) {

    @PostMapping
    @Operation(summary = "Cadastrar cliente", description = "Cadastra um novo cliente no sistema")
    fun cadastrar(@RequestBody request: CadastrarClienteRequest) = clienteUseCase.cadastrar(request.toEntity())
        .let {
            ResponseEntity
                .created(URI("/v1/cliente/${it.id}"))
                .body(it.toResponse())
        }
}