package br.com.felixgilioli.fastfood.adapters.driven.datasource

import com.mercadopago.client.preference.PreferenceClient
import com.mercadopago.client.preference.PreferenceRequest
import com.mercadopago.resources.preference.Preference
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class GeradorLinkPagamentoMercadoPagoDataSourceTest {

    private val preferenceClient: PreferenceClient = mockk()
    private val geradorLinkPagamento = GeradorLinkPagamentoMercadoPagoDataSource("fake-access-token", preferenceClient)

    @Test
    fun retornaLinkPagamentoQuandoRequisicaoBemSucedida() {
        val valor = BigDecimal("100.00")
        val preference = mockk<Preference> {
            every { sandboxInitPoint } returns "http://sandbox.mercadopago.com/link"
        }
        every { preferenceClient.create(any<PreferenceRequest>()) } returns preference

        val link = geradorLinkPagamento.gerarLink(valor)

        assertEquals("http://sandbox.mercadopago.com/link", link)
    }

    @Test
    fun lancaExcecaoQuandoErroNaCriacaoDoLink() {
        val valor = BigDecimal("100.00")
        every { preferenceClient.create(any<PreferenceRequest>()) } throws RuntimeException("Erro ao criar link")

        assertThrows(RuntimeException::class.java) {
            geradorLinkPagamento.gerarLink(valor)
        }
    }
}