package br.com.felixgilioli.fastfood.application.usecases

import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import br.com.felixgilioli.fastfood.domain.entities.Categoria
import br.com.felixgilioli.fastfood.domain.entities.Produto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class BuscarProdutosPeloIdUseCaseTest {

    private lateinit var produtoGateway: ProdutoGateway
    private lateinit var useCase: BuscarProdutoPeloIdUseCase

    @BeforeEach
    fun setUp() {
        produtoGateway = mockk()
        useCase = BuscarProdutoPeloIdUseCase(produtoGateway)
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
        every { produtoGateway.findById(produtoId) } returns produto

        val resultado = useCase.execute(produtoId)

        assertEquals(produto, resultado)
        verify { produtoGateway.findById(produtoId) }
    }

    @Test
    fun retornaNuloQuandoProdutoNaoExistePorId() {
        val produtoId = UUID.randomUUID()
        every { produtoGateway.findById(produtoId) } returns null

        val resultado = useCase.execute(produtoId)

        assertNull(resultado)
        verify { produtoGateway.findById(produtoId) }
    }
}