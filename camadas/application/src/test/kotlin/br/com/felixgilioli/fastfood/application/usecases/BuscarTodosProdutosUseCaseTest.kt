package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import br.com.felixgilioli.fastfood.application.usecases.produto.BuscarTodosProdutosUseCase
import br.com.felixgilioli.fastfood.domain.entities.Categoria
import br.com.felixgilioli.fastfood.domain.entities.Produto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class BuscarTodosProdutosUseCaseTest {

    private lateinit var produtoGateway: ProdutoGateway
    private lateinit var useCase: BuscarTodosProdutosUseCase

    @BeforeEach
    fun setUp() {
        produtoGateway = mockk()
        useCase = BuscarTodosProdutosUseCase(produtoGateway)
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
        every { produtoGateway.findAll() } returns produtos

        val resultado = useCase.execute()

        assertEquals(2, resultado.size)
        assertEquals("Produto 1", resultado[0].nome)
        verify { produtoGateway.findAll() }
    }
}