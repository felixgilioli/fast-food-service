package br.com.felixgilioli.fastfood.infrastructure.config

import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.application.gateways.PedidoGateway
import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.application.ports.driven.GeradorLinkPagamento
import br.com.felixgilioli.fastfood.application.usecases.cliente.CadastrarClienteUseCase
import br.com.felixgilioli.fastfood.application.usecases.listener.AtualizarPedidoLinkPagamentoGeradoListener
import br.com.felixgilioli.fastfood.application.usecases.listener.PagamentoAprovadoListener
import br.com.felixgilioli.fastfood.application.usecases.listener.PagamentoRecusadoListener
import br.com.felixgilioli.fastfood.application.usecases.listener.SolicitarPagamentoListener
import br.com.felixgilioli.fastfood.application.usecases.pagamento.AprovarPagamentoUseCase
import br.com.felixgilioli.fastfood.application.usecases.pagamento.BuscarPagamentoByPedidoUseCase
import br.com.felixgilioli.fastfood.application.usecases.pagamento.RecusarPagamentoUseCase
import br.com.felixgilioli.fastfood.application.usecases.pedido.*
import br.com.felixgilioli.fastfood.application.usecases.produto.*
import com.mercadopago.client.preference.PreferenceClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class BeanConfig {

    @Bean
    fun cadastrarClienteUseCase(clienteGateway: ClienteGateway) = CadastrarClienteUseCase(clienteGateway)

    @Bean
    fun buscarPagamentoByPedidoUseCase(pagamentoGateway: PagamentoGateway) =
        BuscarPagamentoByPedidoUseCase(pagamentoGateway)

    @Bean
    fun aprovarPagamentoUseCase(pagamentoGateway: PagamentoGateway, eventPublisher: EventPublisher) =
        AprovarPagamentoUseCase(pagamentoGateway, eventPublisher)

    @Bean
    fun recusarPagamentoUseCase(pagamentoGateway: PagamentoGateway, eventPublisher: EventPublisher) =
        RecusarPagamentoUseCase(pagamentoGateway, eventPublisher)

    @Bean
    fun buscarTodosProdutosUseCase(produtoGateway: ProdutoGateway) =
        BuscarTodosProdutosUseCase(produtoGateway)

    @Bean
    fun buscarProdutosPeloIdUseCase(produtoGateway: ProdutoGateway) =
        BuscarProdutoPeloIdUseCase(produtoGateway)

    @Bean
    fun buscarProdutosPelaCategoriaUseCase(produtoGateway: ProdutoGateway) =
        BuscarProdutosPelaCategoriaUseCase(produtoGateway)

    @Bean
    fun cadastrarProdutoUseCase(produtoGateway: ProdutoGateway) =
        CadastrarProdutoUseCase(produtoGateway)

    @Bean
    fun atualizarProdutoUseCase(produtoGateway: ProdutoGateway) =
        AtualizarProdutoUseCase(produtoGateway)

    @Bean
    fun novoPedidoUseCase(clienteGateway: ClienteGateway, pedidoGateway: PedidoGateway) =
        NovoPedidoUseCase(clienteGateway, pedidoGateway)

    @Bean
    fun buscarPedidosAguardandoConfirmacaoCozinhaUseCase(pedidoGateway: PedidoGateway) =
        BuscarPedidosAguardandoConfirmacaoCozinhaUseCase(pedidoGateway)

    @Bean
    fun confirmarPedidoCozinhaUseCase(pedidoGateway: PedidoGateway) =
        ConfirmarPedidoCozinhaUseCase(pedidoGateway)

    @Bean
    fun buscarPedidoPeloIdUseCase(pedidoGateway: PedidoGateway) =
        BuscarPedidoPeloIdUseCase(pedidoGateway)

    @Bean
    fun definirPedidoProntoUseCase(pedidoGateway: PedidoGateway) =
        DefinirPedidoProntoUseCase(pedidoGateway)

    @Bean
    fun retirarPedidoUseCase(pedidoGateway: PedidoGateway) =
        RetirarPedidoUseCase(pedidoGateway)

    @Bean
    fun confirmarPedidoUseCase(
        pedidoGateway: PedidoGateway,
        produtoGateway: ProdutoGateway,
        eventPublisher: EventPublisher
    ) = ConfirmarPedidoUseCase(pedidoGateway, produtoGateway, eventPublisher)

    @Bean
    fun solicitarPagamentoUseCase(
        geradorLinkPagamento: GeradorLinkPagamento,
        @Lazy eventPublisher: EventPublisher
    ) = SolicitarPagamentoListener(geradorLinkPagamento, eventPublisher)

    @Bean
    fun atualizarPedidoLinkPagamentoGeradoUseCase(
        pagamentoGateway: PagamentoGateway,
        pedidoGateway: PedidoGateway
    ) = AtualizarPedidoLinkPagamentoGeradoListener(pagamentoGateway, pedidoGateway)

    @Bean
    fun pagamentoAprovadoListener(pedidoGateway: PedidoGateway) = PagamentoAprovadoListener(pedidoGateway)

    @Bean
    fun pagamentoRecusadoListener(pedidoGateway: PedidoGateway) = PagamentoRecusadoListener(pedidoGateway)

    @Bean
    fun preferenceClient() = PreferenceClient()
}