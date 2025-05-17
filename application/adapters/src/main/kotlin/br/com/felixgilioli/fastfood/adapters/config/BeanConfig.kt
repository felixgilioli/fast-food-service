package br.com.felixgilioli.fastfood.adapters.config

import br.com.felixgilioli.fastfood.core.ports.driven.*
import br.com.felixgilioli.fastfood.core.usecases.*
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
    ) = SolicitarPagamentoUseCaseImpl(geradorLinkPagamento, eventPublisher)

    @Bean
    fun atualizarPedidoLinkPagamentoGeradoUseCase(
        pagamentoRepository: PagamentoRepository,
        pedidoRepository: PedidoRepository
    ) = AtualizarPedidoLinkPagamentoGeradoUseCaseImpl(pagamentoRepository, pedidoRepository)

    @Bean
    fun pagamentoUseCase(pagamentoRepository: PagamentoRepository) = PagamentoUseCaseImpl(pagamentoRepository)
}