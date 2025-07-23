package br.com.felixgilioli.fastfood.application.ports.driven

import java.math.BigDecimal

interface GeradorLinkPagamento {

    fun gerarLink(valor: BigDecimal): String
}