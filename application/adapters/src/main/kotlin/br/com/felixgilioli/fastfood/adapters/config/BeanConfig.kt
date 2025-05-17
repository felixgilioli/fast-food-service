package br.com.felixgilioli.fastfood.adapters.config

import br.com.felixgilioli.fastfood.core.ports.driven.*
import br.com.felixgilioli.fastfood.core.usecases.ClienteUseCaseImpl
import br.com.felixgilioli.fastfood.core.usecases.PagamentoUseCaseImpl
import br.com.felixgilioli.fastfood.core.usecases.PedidoUseCaseImpl
import br.com.felixgilioli.fastfood.core.usecases.ProdutoUseCaseImpl
import br.com.felixgilioli.fastfood.core.usecases.listener.AtualizarPedidoLinkPagamentoGeradoListener
import br.com.felixgilioli.fastfood.core.usecases.listener.PagamentoAprovadoListener
import br.com.felixgilioli.fastfood.core.usecases.listener.PagamentoRecusadoListener
import br.com.felixgilioli.fastfood.core.usecases.listener.SolicitarPagamentoListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class BeanConfig {

    @Bean
    fun cadastrarClienteUseCase(clienteRepository: ClienteRepository) = ClienteUseCaseImpl(clienteRepository)

    @Bean
    fun pedidoUseCase(
        pedidoRepository: PedidoRepository,
        clienteRepository: ClienteRepository,
        produtoRepository: ProdutoRepository,
        eventPublisher: EventPublisher
    ) = PedidoUseCaseImpl(clienteRepository, pedidoRepository, produtoRepository, eventPublisher)

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
}