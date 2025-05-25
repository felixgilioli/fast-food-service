package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.PagamentoResponse
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.toResponse
import br.com.felixgilioli.fastfood.core.ports.driver.PagamentoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/pagamento")
class PagamentoController(private val pagamentoUseCase: PagamentoUseCase) {

    @GetMapping
    fun getPagamento(@RequestParam pedidoId: String): ResponseEntity<PagamentoResponse> =
        pagamentoUseCase.getPagamentoByPedido(UUID.fromString(pedidoId))?.toResponse()
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping("/webhook/aprovar")
    fun aprovarPagamento(@RequestParam pagamentoId: String): ResponseEntity<PagamentoResponse> =
        pagamentoUseCase.aprovarPagamento(UUID.fromString(pagamentoId))
            .let { ResponseEntity.noContent().build() }

    @PostMapping("/webhook/recusar")
    fun recusarPagamento(@RequestParam pagamentoId: String): ResponseEntity<PagamentoResponse> =
        pagamentoUseCase.recusarPagamento(UUID.fromString(pagamentoId))
            .let { ResponseEntity.noContent().build() }
}