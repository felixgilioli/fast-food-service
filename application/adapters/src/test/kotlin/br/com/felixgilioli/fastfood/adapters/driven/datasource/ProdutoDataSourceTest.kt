package br.com.felixgilioli.fastfood.adapters.driven.datasource

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.CategoriaORM
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.ProdutoORM
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository.CategoriaORMRepository
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository.ProdutoORMRepository
import br.com.felixgilioli.fastfood.core.commands.ProdutoCommand
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import java.util.*

class ProdutoDataSourceTest {

    private val produtoORMRepository: ProdutoORMRepository = mockk()
    private val categoriaORMRepository: CategoriaORMRepository = mockk()
    private val produtoDataSource = ProdutoDataSource(produtoORMRepository, categoriaORMRepository)

    @Test
    fun retornaTodosProdutosComSucesso() {
        val produtosORM = listOf(
            ProdutoORM(
                id = UUID.randomUUID(),
                nome = "Produto 1",
                categoria = CategoriaORM(id = UUID.randomUUID(), descricao = "oi"),
                preco = BigDecimal.TEN
            ),
            ProdutoORM(
                id = UUID.randomUUID(),
                nome = "Produto 2",
                categoria = CategoriaORM(id = UUID.randomUUID(), descricao = "oi"),
                preco = BigDecimal.ONE
            )
        )
        every { produtoORMRepository.findAll() } returns produtosORM

        val resultado = produtoDataSource.findAll()

        assertEquals(2, resultado.size)
        assertEquals("Produto 1", resultado[0].nome)
        assertEquals("Produto 2", resultado[1].nome)
    }

    @Test
    fun retornaProdutosPorIdsComSucesso() {
        val produtoId = UUID.randomUUID()
        val produtosORM = listOf(
            ProdutoORM(
                id = produtoId,
                nome = "Produto 1",
                categoria = CategoriaORM(id = UUID.randomUUID(), descricao = "oi"),
                preco = BigDecimal.TEN
            )
        )
        every { produtoORMRepository.findAllById(listOf(produtoId)) } returns produtosORM

        val resultado = produtoDataSource.findAllById(listOf(produtoId))

        assertEquals(1, resultado.size)
        assertEquals(produtoId, resultado[0].id)
    }

    @Test
    fun retornaProdutoQuandoIdExiste() {
        val produtoId = UUID.randomUUID()
        val produtoORM = ProdutoORM(
            id = produtoId,
            nome = "Produto 1",
            categoria = CategoriaORM(id = UUID.randomUUID(), descricao = "oi"),
            preco = BigDecimal.TEN
        )
        every { produtoORMRepository.findByIdOrNull(produtoId) } returns produtoORM

        val resultado = produtoDataSource.findById(produtoId)

        assertEquals(produtoId, resultado?.id)
        assertEquals("Produto 1", resultado?.nome)
    }

    @Test
    fun retornaNullQuandoProdutoIdNaoExiste() {
        val produtoId = UUID.randomUUID()
        every { produtoORMRepository.findByIdOrNull(produtoId) } returns null

        val resultado = produtoDataSource.findById(produtoId)

        assertEquals(null, resultado)
    }

    @Test
    fun salvaProdutoComSucesso() {
        val categoriaId = UUID.randomUUID()
        val produtoCommand = ProdutoCommand(
            id = UUID.randomUUID(),
            nome = "Produto 1",
            categoriaId = categoriaId,
            preco = BigDecimal.TEN
        )
        val categoriaORM = mockk<CategoriaORM>()
        val produtoORM = ProdutoORM(
            id = UUID.randomUUID(),
            nome = "Produto 1",
            categoria = CategoriaORM(id = categoriaId, descricao = "oi"),
            preco = BigDecimal.TEN
        )
        every { categoriaORMRepository.findByIdOrNull(categoriaId) } returns categoriaORM
        every { produtoORMRepository.save(any()) } returns produtoORM

        val resultado = produtoDataSource.save(produtoCommand)

        assertEquals("Produto 1", resultado.nome)
        assertEquals(BigDecimal.TEN, resultado.preco)
    }

    @Test
    fun lancaExcecaoQuandoCategoriaNaoExisteAoSalvarProduto() {
        val categoriaId = UUID.randomUUID()
        val produtoCommand = ProdutoCommand(
            id = UUID.randomUUID(),
            nome = "Produto 1",
            categoriaId = categoriaId,
            preco = BigDecimal.TEN
        )
        every { categoriaORMRepository.findByIdOrNull(categoriaId) } returns null

        assertThrows(IllegalArgumentException::class.java) {
            produtoDataSource.save(produtoCommand)
        }
    }

    @Test
    fun retornaProdutosPorCategoriaIdComSucesso() {
        val categoriaId = UUID.randomUUID()
        val produtosORM = listOf(
            ProdutoORM(
                id = UUID.randomUUID(),
                nome = "Produto 1",
                categoria = CategoriaORM(id = UUID.randomUUID(), descricao = "oi"),
                preco = BigDecimal.TEN
            ),
            ProdutoORM(
                id = UUID.randomUUID(),
                nome = "Produto 2",
                categoria = CategoriaORM(id = UUID.randomUUID(), descricao = "oi"),
                preco = BigDecimal.ONE
            )
        )
        every { produtoORMRepository.findByCategoriaId(categoriaId) } returns produtosORM

        val resultado = produtoDataSource.findByCategoriaId(categoriaId)

        assertEquals(2, resultado.size)
        assertEquals("Produto 1", resultado[0].nome)
        assertEquals("Produto 2", resultado[1].nome)
    }

    @Test
    fun retornaListaVaziaQuandoNaoHaProdutosParaCategoriaId() {
        val categoriaId = UUID.randomUUID()
        every { produtoORMRepository.findByCategoriaId(categoriaId) } returns emptyList()

        val resultado = produtoDataSource.findByCategoriaId(categoriaId)

        assertEquals(0, resultado.size)
    }
}