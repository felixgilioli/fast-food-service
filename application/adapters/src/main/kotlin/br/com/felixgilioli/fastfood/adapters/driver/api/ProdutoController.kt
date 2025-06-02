package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.request.ProdutoRequest
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.ProdutosPorCategoriaResponse
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.toResponse
import br.com.felixgilioli.fastfood.core.entities.Produto
import br.com.felixgilioli.fastfood.core.ports.driver.ProdutoUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/produto")
@Tag(name = "Produto API", description = "Gerenciamento de produtos")
class ProdutoController(private val produtoUseCase: ProdutoUseCase) {

    @GetMapping
    @Operation(
        summary = "Listar produtos",
        description = "Retorna uma lista de todos os produtos disponíveis no sistema."
    )
    fun findAll() = produtoUseCase
        .findAll()
        .map { it.toResponse() }

    @GetMapping("/categoria")
    @Operation(
        summary = "Listar produtos por categoria",
        description = "Agrupa os produtos por categoria e retorna uma lista de categorias com seus respectivos produtos."
    )
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
    @Operation(
        summary = "Buscar produto por ID",
        description = "Retorna os detalhes de um produto específico com base no seu ID."
    )
    fun findById(@PathVariable id: String) =
        produtoUseCase.findById(UUID.fromString(id))?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/categoria/{categoriaId}")
    @Operation(
        summary = "Buscar produtos por categoria",
        description = "Retorna todos os produtos pertencentes a uma categoria específica."
    )
    fun findByCategoriaId(@PathVariable categoriaId: String) =
        produtoUseCase.findByCategoriaId(UUID.fromString(categoriaId))
            .map { it.toResponse() }

    @PostMapping
    @Operation(
        summary = "Criar novo produto",
        description = "Cria um novo produto no sistema com as informações fornecidas."
    )
    fun create(@RequestBody produto: ProdutoRequest) =
        produtoUseCase.create(produto.toCommand()).toResponse()

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar produto",
        description = "Atualiza as informações de um produto existente com base no ID fornecido."
    )
    fun update(@PathVariable id: String, @RequestBody produto: ProdutoRequest) =
        produtoUseCase.update(produto.toCommand(id)).toResponse()
}