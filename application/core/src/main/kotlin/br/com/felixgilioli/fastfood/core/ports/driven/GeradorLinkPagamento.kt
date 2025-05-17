package br.com.felixgilioli.fastfood.core.ports.driven

import java.math.BigDecimal

interface GeradorLinkPagamento {

    fun gerarLink(valor: BigDecimal): String
}