package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.ConfirmarPedidoRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.NovoPedidoRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.toResponse
import br.com.felixgilioli.fastfood.core.ports.driver.PedidoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/pedido")
class PedidoController(private val pedidoUseCase: PedidoUseCase) {

    @PostMapping("/novo")
    fun novoPedido(@RequestBody request: NovoPedidoRequest) = pedidoUseCase
        .novoPedido(request.toCommand()).toResponse()

    @PostMapping("/confirmar")
    fun confirmarPedido(@RequestBody request: ConfirmarPedidoRequest) = pedidoUseCase
        .confirmarPedido(request.toCommand()).toResponse()

    @GetMapping("/aguardando-confirmacao-cozinha")
    fun findPedidosAguardandoConfirmacaoCozinha() = pedidoUseCase
        .findPedidosAguardandoConfirmacaoCozinha().map { it.toResponse() }

    @PutMapping("/{pedidoId}/confirmar-cozinha")
    fun confirmarPedidoCozinha(@PathVariable pedidoId: String) = pedidoUseCase
        .confirmarPedidoCozinha(UUID.fromString(pedidoId)).toResponse()

    @GetMapping("/{pedidoId}")
    fun acompanharPedido(@PathVariable pedidoId: String) =
        pedidoUseCase.findById(UUID.fromString(pedidoId))?.toResponse()
            ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PutMapping("/{pedidoId}/pronto")
    fun pedidoPronto(@PathVariable pedidoId: String) = pedidoUseCase
        .pedidoPronto(UUID.fromString(pedidoId)).toResponse()

    @PutMapping("/{pedidoId}/retirar")
    fun retirarPedido(@PathVariable pedidoId: String) = pedidoUseCase
        .retirarPedido(UUID.fromString(pedidoId)).toResponse()


}