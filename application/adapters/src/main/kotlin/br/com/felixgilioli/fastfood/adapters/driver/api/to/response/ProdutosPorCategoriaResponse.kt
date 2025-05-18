package br.com.felixgilioli.fastfood.adapters.driver.api.to.response

data class ProdutosPorCategoriaResponse(
    val categoria: String,
    val produtos: List<ProdutoResponse>
)
