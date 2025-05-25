package br.com.felixgilioli.fastfood.adapters.driven.datasource

import br.com.felixgilioli.fastfood.core.ports.driven.GeradorLinkPagamento
import com.mercadopago.MercadoPagoConfig
import com.mercadopago.client.preference.PreferenceBackUrlsRequest
import com.mercadopago.client.preference.PreferenceClient
import com.mercadopago.client.preference.PreferenceItemRequest
import com.mercadopago.client.preference.PreferenceRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal


@Component
class GeradorLinkPagamentoMercadoPagoDataSource(
    @Value("\${mercadopago.access-token}") private val accessToken: String,
    private val preferenceClient: PreferenceClient
) : GeradorLinkPagamento {

    override fun gerarLink(valor: BigDecimal): String {
        MercadoPagoConfig.setAccessToken(accessToken)

        val request = PreferenceRequest.builder()
            .items(
                mutableListOf(
                    PreferenceItemRequest.builder()
                        .title("Checkout")
                        .quantity(1)
                        .unitPrice(valor)
                        .build()
                )
            )
            .backUrls(
                PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:8080/sucesso")
                    .failure("http://localhost:8080/falha")
                    .pending("http://localhost:8080/pendente")
                    .build()
            )
            .build()

        return preferenceClient.create(request).sandboxInitPoint
    }
}