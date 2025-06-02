package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.ConfirmarPedidoRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.NovoPedidoRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.toResponse
import br.com.felixgilioli.fastfood.core.ports.driver.PedidoUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/pedido")
@Tag(name = "Pedido API", description = "Gerenciamento de pedidos")
class PedidoController(private val pedidoUseCase: PedidoUseCase) {

    @PostMapping("/novo")
    @Operation(
        summary = "Novo pedido",
        description = "Cria um novo pedido no sistema sem nenhum item adicionado."
    )
    fun novoPedido(@RequestBody request: NovoPedidoRequest) = pedidoUseCase
        .novoPedido(request.toCommand()).toResponse()

    @PostMapping("/confirmar")
    @Operation(
        summary = "Confirmar pedido",
        description = "Confirma um pedido com os itens especificados."
    )
    fun confirmarPedido(@RequestBody request: ConfirmarPedidoRequest) = pedidoUseCase
        .confirmarPedido(request.toCommand()).toResponse()

    @GetMapping("/aguardando-confirmacao-cozinha")
    @Operation(
        summary = "Pedidos aguardando confirmação da cozinha",
        description = "Lista todos os pedidos que estão aguardando confirmação na cozinha."
    )
    fun findPedidosAguardandoConfirmacaoCozinha() = pedidoUseCase
        .findPedidosAguardandoConfirmacaoCozinha().map { it.toResponse() }

    @PutMapping("/{pedidoId}/confirmar-cozinha")
    @Operation(
        summary = "Confirmar pedido na cozinha",
        description = "Confirma um pedido na cozinha para iniciar o preparo."
    )
    fun confirmarPedidoCozinha(@PathVariable pedidoId: String) = pedidoUseCase
        .confirmarPedidoCozinha(UUID.fromString(pedidoId)).toResponse()

    @GetMapping("/{pedidoId}")
    @Operation(
        summary = "Acompanhar pedido",
        description = "Permite acompanhar o status de um pedido específico."
    )
    fun acompanharPedido(@PathVariable pedidoId: String) =
        pedidoUseCase.findById(UUID.fromString(pedidoId))?.toResponse()
            ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PutMapping("/{pedidoId}/pronto")
    @Operation(
        summary = "Pedido pronto",
        description = "Marca um pedido como pronto para retirada."
    )
    fun pedidoPronto(@PathVariable pedidoId: String) = pedidoUseCase
        .pedidoPronto(UUID.fromString(pedidoId)).toResponse()

    @PutMapping("/{pedidoId}/retirar")
    @Operation(
        summary = "Retirar pedido",
        description = "Marca um pedido como retirado pelo cliente."
    )
    fun retirarPedido(@PathVariable pedidoId: String) = pedidoUseCase
        .retirarPedido(UUID.fromString(pedidoId)).toResponse()


}