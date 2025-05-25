package br.com.felixgilioli.fastfood.core.usecases

import br.com.felixgilioli.fastfood.core.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.core.entities.Categoria
import br.com.felixgilioli.fastfood.core.entities.Produto
import br.com.felixgilioli.fastfood.core.ports.driven.ProdutoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class ProdutoUseCaseImplTest {

    private lateinit var produtoRepository: ProdutoRepository
    private lateinit var produtoUseCase: ProdutoUseCaseImpl

    @BeforeEach
    fun setUp() {
        produtoRepository = mockk()
        produtoUseCase = ProdutoUseCaseImpl(produtoRepository)
    }

    @Test
    fun retornaTodosProdutosComSucesso() {
        val produtos = listOf(
            Produto(
                id = UUID.randomUUID(),
                nome = "Produto 1",
                preco = BigDecimal.TEN,
                categoria = Categoria(descricao = "Teste"),
            ),
            Produto(
                id = UUID.randomUUID(),
                nome = "Produto 2",
                preco = BigDecimal.TEN,
                categoria = Categoria(descricao = "Teste"),
            )
        )
        every { produtoRepository.findAll() } returns produtos

        val resultado = produtoUseCase.findAll()

        assertEquals(2, resultado.size)
        assertEquals("Produto 1", resultado[0].nome)
        verify { produtoRepository.findAll() }
    }

    @Test
    fun retornaProdutoPorIdQuandoExiste() {
        val produtoId = UUID.randomUUID()
        val produto = Produto(
            id = UUID.randomUUID(),
            nome = "Produto 1",
            preco = BigDecimal.TEN,
            categoria = Categoria(descricao = "Teste"),
        )
        every { produtoRepository.findById(produtoId) } returns produto

        val resultado = produtoUseCase.findById(produtoId)

        assertEquals(produto, resultado)
        verify { produtoRepository.findById(produtoId) }
    }

    @Test
    fun retornaNuloQuandoProdutoNaoExistePorId() {
        val produtoId = UUID.randomUUID()
        every { produtoRepository.findById(produtoId) } returns null

        val resultado = produtoUseCase.findById(produtoId)

        assertNull(resultado)
        verify { produtoRepository.findById(produtoId) }
    }

    @Test
    fun retornaProdutosPorCategoriaIdComSucesso() {
        val categoriaId = UUID.randomUUID()
        val produtos = listOf(
            Produto(
                id = UUID.randomUUID(),
                nome = "Produto 1",
                preco = BigDecimal.TEN,
                categoria = Categoria(descricao = "Teste"),
            ),
            Produto(
                id = UUID.randomUUID(),
                nome = "Produto 2",
                preco = BigDecimal.TEN,
                categoria = Categoria(descricao = "Teste"),
            )
        )
        every { produtoRepository.findByCategoriaId(categoriaId) } returns produtos

        val resultado = produtoUseCase.findByCategoriaId(categoriaId)

        assertEquals(2, resultado.size)
        verify { produtoRepository.findByCategoriaId(categoriaId) }
    }

    @Test
    fun criaProdutoComSucesso() {
        val produtoCommand =
            ProdutoCommand(nome = "Produto Novo", preco = BigDecimal.TEN, categoriaId = UUID.randomUUID())
        val produto = Produto(
            id = UUID.randomUUID(),
            nome = produtoCommand.nome,
            preco = produtoCommand.preco,
            categoria = Categoria(descricao = "Teste")
        )
        every { produtoRepository.save(produtoCommand) } returns produto

        val resultado = produtoUseCase.create(produtoCommand)

        assertEquals(produto.nome, resultado.nome)
        verify { produtoRepository.save(produtoCommand) }
    }

    @Test
    fun atualizaProdutoComSucesso() {
        val produtoCommand =
            ProdutoCommand(nome = "Produto Atualizado", preco = BigDecimal.ONE, categoriaId = UUID.randomUUID())
        val produto = Produto(
            id = UUID.randomUUID(),
            nome = produtoCommand.nome,
            preco = produtoCommand.preco,
            categoria = Categoria(descricao = "Teste")
        )
        every { produtoRepository.save(produtoCommand) } returns produto

        val resultado = produtoUseCase.update(produtoCommand)

        assertEquals(produto.nome, resultado.nome)
        verify { produtoRepository.save(produtoCommand) }
    }
}