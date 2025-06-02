package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.PagamentoResponse
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.toResponse
import br.com.felixgilioli.fastfood.core.ports.driver.PagamentoUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/pagamento")
@Tag(name = "Pagamento API", description = "Gerenciamento de pagamentos")
class PagamentoController(private val pagamentoUseCase: PagamentoUseCase) {

    @GetMapping
    @Operation(
        summary = "Obter pagamento por ID do pedido",
        description = "Recupera o pagamento associado a um pedido específico"
    )
    fun getPagamento(@RequestParam pedidoId: String): ResponseEntity<PagamentoResponse> =
        pagamentoUseCase.getPagamentoByPedido(UUID.fromString(pedidoId))?.toResponse()
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping("/webhook/aprovar")
    @Operation(summary = "Aprovar pagamento via webhook", description = "Aprova um pagamento recebido via webhook")
    fun aprovarPagamento(@RequestParam pagamentoId: String): ResponseEntity<PagamentoResponse> =
        pagamentoUseCase.aprovarPagamento(UUID.fromString(pagamentoId))
            .let { ResponseEntity.noContent().build() }

    @PostMapping("/webhook/recusar")
    @Operation(summary = "Recusar pagamento via webhook", description = "Recusa um pagamento recebido via webhook")
    fun recusarPagamento(@RequestParam pagamentoId: String): ResponseEntity<PagamentoResponse> =
        pagamentoUseCase.recusarPagamento(UUID.fromString(pagamentoId))
            .let { ResponseEntity.noContent().build() }
}