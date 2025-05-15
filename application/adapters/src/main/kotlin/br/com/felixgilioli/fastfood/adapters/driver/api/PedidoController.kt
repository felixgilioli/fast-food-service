package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.NovoPedidoRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.toResponse
import br.com.felixgilioli.fastfood.core.ports.driver.PedidoUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/pedido")
class PedidoController(private val pedidoUseCase: PedidoUseCase) {

    @PostMapping("/novo")
    fun novoPedido(@RequestBody novoPedidoRequest: NovoPedidoRequest) = pedidoUseCase
        .novoPedido(novoPedidoRequest.toCommand()).toResponse()

}