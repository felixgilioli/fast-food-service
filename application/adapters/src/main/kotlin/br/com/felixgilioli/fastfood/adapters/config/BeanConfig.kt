package br.com.felixgilioli.fastfood.adapters.config

import br.com.felixgilioli.fastfood.core.ports.driven.*
import br.com.felixgilioli.fastfood.core.ports.driver.ClienteUseCase
import br.com.felixgilioli.fastfood.core.ports.driver.PedidoUseCase
import br.com.felixgilioli.fastfood.core.ports.driver.ProdutoUseCase
import br.com.felixgilioli.fastfood.core.usecases.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class BeanConfig {

    @Bean
    fun cadastrarClienteUseCase(clienteRepository: ClienteRepository): ClienteUseCase {
        return ClienteUseCaseImpl(clienteRepository)
    }

    @Bean
    fun pedidoUseCase(
        pedidoRepository: PedidoRepository,
        clienteRepository: ClienteRepository,
        produtoRepository: ProdutoRepository,
        eventPublisher: EventPublisher
    ): PedidoUseCase {
        return PedidoUseCaseImpl(clienteRepository, pedidoRepository, produtoRepository, eventPublisher)
    }

    @Bean
    fun produtoUseCase(produtoRepository: ProdutoRepository): ProdutoUseCase {
        return ProdutoUseCaseImpl(produtoRepository)
    }

    @Bean
    fun solicitarPagamentoUseCase(
        geradorLinkPagamento: GeradorLinkPagamento,
        @Lazy eventPublisher: EventPublisher
    ): SolicitarPagamentoUseCaseImpl {
        return SolicitarPagamentoUseCaseImpl(geradorLinkPagamento, eventPublisher)
    }

    @Bean
    fun atualizarPedidoLinkPagamentoGeradoUseCase(
        pagamentoRepository: PagamentoRepository,
        pedidoRepository: PedidoRepository
    ): AtualizarPedidoLinkPagamentoGeradoUseCaseImpl {
        return AtualizarPedidoLinkPagamentoGeradoUseCaseImpl(pagamentoRepository, pedidoRepository)
    }
}