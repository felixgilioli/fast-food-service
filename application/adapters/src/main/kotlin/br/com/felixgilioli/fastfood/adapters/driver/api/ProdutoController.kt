package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.ProdutoRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.ProdutosPorCategoriaResponse
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.toResponse
import br.com.felixgilioli.fastfood.core.entities.Produto
import br.com.felixgilioli.fastfood.core.ports.driver.ProdutoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/produto")
class ProdutoController(private val produtoUseCase: ProdutoUseCase) {

    @GetMapping
    fun findAll() = produtoUseCase
        .findAll()
        .map { it.toResponse() }

    @GetMapping("/categoria")
    fun findAllGroupByCategoria() = produtoUseCase
        .findAll()
        .groupBy { it.categoria }
        .map { (categoria, produtos) ->
            ProdutosPorCategoriaResponse(
                categoria = categoria.descricao,
                produtos = produtos.map(Produto::toResponse)
            )
        }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) =
        produtoUseCase.findById(UUID.fromString(id))?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/categoria/{categoriaId}")
    fun findByCategoriaId(@PathVariable categoriaId: String) =
        produtoUseCase.findByCategoriaId(UUID.fromString(categoriaId))
            .map { it.toResponse() }

    @PostMapping
    fun create(@RequestBody produto: ProdutoRequest) =
        produtoUseCase.create(produto.toCommand()).toResponse()

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody produto: ProdutoRequest) =
        produtoUseCase.update(produto.toCommand(id)).toResponse()
}