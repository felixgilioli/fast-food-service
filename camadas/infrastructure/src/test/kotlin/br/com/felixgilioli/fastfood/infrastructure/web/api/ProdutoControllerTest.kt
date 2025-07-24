package br.com.felixgilioli.fastfood.infrastructure.driver.api

import br.com.felixgilioli.fastfood.application.usecases.*
import br.com.felixgilioli.fastfood.application.usecases.produto.AtualizarProdutoUseCase
import br.com.felixgilioli.fastfood.application.usecases.produto.BuscarProdutoPeloIdUseCase
import br.com.felixgilioli.fastfood.application.usecases.produto.BuscarProdutosPelaCategoriaUseCase
import br.com.felixgilioli.fastfood.application.usecases.produto.BuscarTodosProdutosUseCase
import br.com.felixgilioli.fastfood.application.usecases.produto.CadastrarProdutoUseCase
import br.com.felixgilioli.fastfood.domain.entities.Categoria
import br.com.felixgilioli.fastfood.domain.entities.Produto
import br.com.felixgilioli.fastfood.infrastructure.driver.api.to.request.ProdutoRequest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.util.*

class ProdutoControllerTest {

    private val buscarTodosProdutosUseCase: BuscarTodosProdutosUseCase = mockk()
    private val buscarProdutoPeloIdUseCase: BuscarProdutoPeloIdUseCase = mockk()
    private val buscarProdutosPelaCategoriaUseCase: BuscarProdutosPelaCategoriaUseCase = mockk()
    private val cadastrarProdutoUseCase: CadastrarProdutoUseCase = mockk()
    private val atualizarProdutoUseCase: AtualizarProdutoUseCase = mockk()

    private val produtoController = ProdutoController(
        buscarTodosProdutosUseCase,
        buscarProdutoPeloIdUseCase,
        buscarProdutosPelaCategoriaUseCase,
        cadastrarProdutoUseCase,
        atualizarProdutoUseCase
    )

    @Test
    fun retornaTodosProdutosComSucesso() {
        val produtos = listOf(
            Produto(
                id = UUID.randomUUID(),
                nome = "Produto 1",
                categoria = Categoria(UUID.randomUUID(), descricao = "Categoria 1"),
                preco = BigDecimal.TEN
            ),
            Produto(
                id = UUID.randomUUID(),
                nome = "Produto 2",
                categoria = Categoria(UUID.randomUUID(), "Categoria 2"),
                preco = BigDecimal(20.0)
            )
        )
        every { buscarTodosProdutosUseCase.execute() } returns produtos

        val response = produtoController.findAll()

        assertNotNull(response)
        assertEquals(2, response.size)
        assertEquals(produtos[0].nome, response[0].nome)
        assertEquals(produtos[1].nome, response[1].nome)
    }

    @Test
    fun retornaProdutoPorIdComSucesso() {
        val produtoId = UUID.randomUUID()
        val produto = Produto(
            id = produtoId,
            nome = "Produto 1",
            categoria = Categoria(UUID.randomUUID(), "Categoria 1"),
            preco = BigDecimal.TEN
        )
        every { buscarProdutoPeloIdUseCase.execute(produtoId) } returns produto

        val response = produtoController.findById(produtoId.toString())

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(produto.nome, response.body?.nome)
    }

    @Test
    fun retornaNotFoundQuandoProdutoNaoExiste() {
        val produtoId = UUID.randomUUID()
        every { buscarProdutoPeloIdUseCase.execute(produtoId) } returns null

        val response = produtoController.findById(produtoId.toString())

        assertNotNull(response)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun criaProdutoComSucesso() {
        val request =
            ProdutoRequest(nome = "Produto 1", categoriaId = UUID.randomUUID().toString(), preco = BigDecimal.TEN)
        val produto = Produto(
            id = UUID.randomUUID(),
            nome = request.nome,
            categoria = Categoria(UUID.fromString(request.categoriaId), "Categoria 1"),
            preco = request.preco
        )
        every { cadastrarProdutoUseCase.execute(any()) } returns produto

        val response = produtoController.create(request)

        assertNotNull(response)
        assertEquals(produto.nome, response.nome)
        assertEquals(produto.preco, response.preco)
    }

    @Test
    fun atualizaProdutoComSucesso() {
        val produtoId = UUID.randomUUID()
        val request = ProdutoRequest(
            nome = "Produto Atualizado",
            categoriaId = UUID.randomUUID().toString(),
            preco = BigDecimal(15.0)
        )
        val produto = Produto(
            id = produtoId,
            nome = request.nome,
            categoria = Categoria(UUID.fromString(request.categoriaId), "Categoria Atualizada"),
            preco = request.preco
        )
        every { atualizarProdutoUseCase.execute(any()) } returns produto

        val response = produtoController.update(produtoId.toString(), request)

        assertNotNull(response)
        assertEquals(produto.nome, response.nome)
        assertEquals(produto.preco, response.preco)
    }

    @Test
    fun retornaProdutosAgrupadosPorCategoria() {
        val categoria1 = Categoria(UUID.randomUUID(), "Categoria 1")
        val categoria2 = Categoria(UUID.randomUUID(), "Categoria 2")
        val produtos = listOf(
            Produto(UUID.randomUUID(), "Produto 1", categoria1, BigDecimal.TEN),
            Produto(UUID.randomUUID(), "Produto 2", categoria2, BigDecimal(20.0))
        )
        every { buscarTodosProdutosUseCase.execute() } returns produtos

        val response = produtoController.findAllGroupByCategoria()

        assertNotNull(response)
        assertEquals(2, response.size)
        assertEquals("Categoria 1", response[0].categoria)
        assertEquals("Categoria 2", response[1].categoria)
    }
}