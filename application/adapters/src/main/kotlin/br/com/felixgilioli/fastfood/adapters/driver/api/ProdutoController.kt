package br.com.felixgilioli.fastfood.adapters.driver.api

import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.ProdutoResponse
import br.com.felixgilioli.fastfood.adapters.driver.api.to.response.ProdutosPorCategoriaResponse
import br.com.felixgilioli.fastfood.core.ports.driver.ProdutoUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/produto")
class ProdutoController(private val produtoUseCase: ProdutoUseCase) {

    @GetMapping
    fun findAll(): List<ProdutosPorCategoriaResponse> = produtoUseCase
        .findAll()
        .groupBy { it.categoria }
        .map { (categoria, produtos) ->
            ProdutosPorCategoriaResponse(
                categoria = categoria.descricao,
                produtos = produtos.map { produto ->
                    ProdutoResponse(
                        produto.id.toString(),
                        produto.nome,
                        produto.preco,
                        produto.descricao
                    )
                }
            )
        }


}