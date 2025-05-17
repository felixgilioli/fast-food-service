package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.toResponse
import br.com.felixgilioli.fastfood.core.ports.driver.PagamentoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/pagamento")
class PagamentoController(private val pagamentoUseCase: PagamentoUseCase) {

    @GetMapping
    fun getPagamento(@RequestParam pedidoId: String): ResponseEntity<Any> =
        pagamentoUseCase.getPagamentoByPedido(UUID.fromString(pedidoId))?.toResponse()
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}