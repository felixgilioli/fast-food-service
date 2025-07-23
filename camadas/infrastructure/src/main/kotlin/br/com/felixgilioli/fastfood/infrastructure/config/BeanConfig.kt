package br.com.felixgilioli.fastfood.infrastructure.config

import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.application.gateways.PagamentoGateway
import br.com.felixgilioli.fastfood.application.ports.driven.EventPublisher
import br.com.felixgilioli.fastfood.application.ports.driven.GeradorLinkPagamento
import br.com.felixgilioli.fastfood.application.ports.driven.PedidoRepository
import br.com.felixgilioli.fastfood.application.ports.driven.ProdutoRepository
import br.com.felixgilioli.fastfood.application.usecases.*
import br.com.felixgilioli.fastfood.application.usecases.listener.AtualizarPedidoLinkPagamentoGeradoListener
import br.com.felixgilioli.fastfood.application.usecases.listener.PagamentoAprovadoListener
import br.com.felixgilioli.fastfood.application.usecases.listener.PagamentoRecusadoListener
import br.com.felixgilioli.fastfood.application.usecases.listener.SolicitarPagamentoListener
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
    fun pedidoUseCase(
        pedidoRepository: PedidoRepository,
        clienteGateway: ClienteGateway,
        produtoRepository: ProdutoRepository,
        eventPublisher: EventPublisher
    ) = PedidoUseCaseImpl(clienteGateway, pedidoRepository, produtoRepository, eventPublisher)

    @Bean
    fun produtoUseCase(produtoRepository: ProdutoRepository) = ProdutoUseCaseImpl(produtoRepository)

    @Bean
    fun solicitarPagamentoUseCase(
        geradorLinkPagamento: GeradorLinkPagamento,
        @Lazy eventPublisher: EventPublisher
    ) = SolicitarPagamentoListener(geradorLinkPagamento, eventPublisher)

    @Bean
    fun atualizarPedidoLinkPagamentoGeradoUseCase(
        pagamentoGateway: PagamentoGateway,
        pedidoRepository: PedidoRepository
    ) = AtualizarPedidoLinkPagamentoGeradoListener(pagamentoGateway, pedidoRepository)

    @Bean
    fun pagamentoUseCase(pagamentoGateway: PagamentoGateway, eventPublisher: EventPublisher) =
        PagamentoUseCaseImpl(pagamentoGateway, eventPublisher)

    @Bean
    fun pagamentoAprovadoListener(pedidoRepository: PedidoRepository) = PagamentoAprovadoListener(pedidoRepository)

    @Bean
    fun pagamentoRecusadoListener(pedidoRepository: PedidoRepository) = PagamentoRecusadoListener(pedidoRepository)

    @Bean
    fun preferenceClient() = PreferenceClient()
}