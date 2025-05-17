package br.com.felixgilioli.fastfood.core.entities

enum class StatusPedido {
    CRIADO,
    PEDIDO_CONFIRMADO,
    PAGAMENTO_SOLICITADO,
    PAGAMENTO_APROVADO,
    PAGAMENTO_REPROVADO,
    CONFIRMADO_PELA_COZINHA,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO
}