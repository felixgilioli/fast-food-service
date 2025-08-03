package br.com.felixgilioli.fastfood.infrastructure.web.api.dto.response

data class ProdutosPorCategoriaResponse(
    val categoria: String,
    val produtos: List<ProdutoResponse>
)
