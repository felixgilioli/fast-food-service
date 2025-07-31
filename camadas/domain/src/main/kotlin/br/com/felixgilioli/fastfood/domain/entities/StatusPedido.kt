package br.com.felixgilioli.fastfood.domain.entities

enum class StatusPedido {
    CRIADO,
    PEDIDO_CONFIRMADO,
    PAGAMENTO_SOLICITADO,
    PAGAMENTO_APROVADO,
    PAGAMENTO_RECUSADO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO
}