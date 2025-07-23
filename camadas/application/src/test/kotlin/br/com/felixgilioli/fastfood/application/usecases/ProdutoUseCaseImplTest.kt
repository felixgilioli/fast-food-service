package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import br.com.felixgilioli.fastfood.domain.entities.Categoria
import br.com.felixgilioli.fastfood.domain.entities.Produto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class ProdutoUseCaseImplTest {

    private lateinit var produtoGateway: ProdutoGateway
    private lateinit var produtoUseCase: ProdutoUseCaseImpl

    @BeforeEach
    fun setUp() {
        produtoGateway = mockk()
        produtoUseCase = ProdutoUseCaseImpl(produtoGateway)
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
        every { produtoGateway.findByCategoriaId(categoriaId) } returns produtos

        val resultado = produtoUseCase.findByCategoriaId(categoriaId)

        assertEquals(2, resultado.size)
        verify { produtoGateway.findByCategoriaId(categoriaId) }
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
        every { produtoGateway.save(produtoCommand) } returns produto

        val resultado = produtoUseCase.create(produtoCommand)

        assertEquals(produto.nome, resultado.nome)
        verify { produtoGateway.save(produtoCommand) }
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
        every { produtoGateway.save(produtoCommand) } returns produto

        val resultado = produtoUseCase.update(produtoCommand)

        assertEquals(produto.nome, resultado.nome)
        verify { produtoGateway.save(produtoCommand) }
    }
}