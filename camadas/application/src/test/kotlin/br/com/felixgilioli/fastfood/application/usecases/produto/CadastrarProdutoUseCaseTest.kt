package br.com.felixgilioli.fastfood.application.usecases.produto

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

class CadastrarProdutoUseCaseTest {

    private lateinit var produtoGateway: ProdutoGateway
    private lateinit var useCase: CadastrarProdutoUseCase

    @BeforeEach
    fun setUp() {
        produtoGateway = mockk()
        useCase = CadastrarProdutoUseCase(produtoGateway)
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

        val resultado = useCase.execute(produtoCommand)

        assertEquals(produto.nome, resultado.nome)
        verify { produtoGateway.save(produtoCommand) }
    }
}