package br.com.felixgilioli.fastfood.infrastructure.config

import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.application.ports.driven.*
import br.com.felixgilioli.fastfood.application.usecases.impl.CadastrarClienteUseCaseImpl
import br.com.felixgilioli.fastfood.application.usecases.impl.PagamentoUseCaseImpl
import br.com.felixgilioli.fastfood.application.usecases.impl.PedidoUseCaseImpl
import br.com.felixgilioli.fastfood.application.usecases.impl.ProdutoUseCaseImpl
import br.com.felixgilioli.fastfood.application.usecases.impl.listener.AtualizarPedidoLinkPagamentoGeradoListener
import br.com.felixgilioli.fastfood.application.usecases.impl.listener.PagamentoAprovadoListener
import br.com.felixgilioli.fastfood.application.usecases.impl.listener.PagamentoRecusadoListener
import br.com.felixgilioli.fastfood.application.usecases.impl.listener.SolicitarPagamentoListener
import com.mercadopago.client.preference.PreferenceClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class BeanConfig {

    @Bean
    fun cadastrarClienteUseCase(clienteGateway: ClienteGateway) = CadastrarClienteUseCaseImpl(clienteGateway)

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
        pagamentoRepository: PagamentoRepository,
        pedidoRepository: PedidoRepository
    ) = AtualizarPedidoLinkPagamentoGeradoListener(pagamentoRepository, pedidoRepository)

    @Bean
    fun pagamentoUseCase(pagamentoRepository: PagamentoRepository, eventPublisher: EventPublisher) =
        PagamentoUseCaseImpl(pagamentoRepository, eventPublisher)

    @Bean
    fun pagamentoAprovadoListener(pedidoRepository: PedidoRepository) = PagamentoAprovadoListener(pedidoRepository)

    @Bean
    fun pagamentoRecusadoListener(pedidoRepository: PedidoRepository) = PagamentoRecusadoListener(pedidoRepository)

    @Bean
    fun preferenceClient() = PreferenceClient()
}