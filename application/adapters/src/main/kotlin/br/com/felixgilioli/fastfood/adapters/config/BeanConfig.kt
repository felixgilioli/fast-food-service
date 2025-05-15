package br.com.felixgilioli.fastfood.adapters.config

import br.com.felixgilioli.fastfood.core.ports.driven.ClienteRepository
import br.com.felixgilioli.fastfood.core.ports.driven.PedidoRepository
import br.com.felixgilioli.fastfood.core.ports.driven.ProdutoRepository
import br.com.felixgilioli.fastfood.core.ports.driver.ClienteUseCase
import br.com.felixgilioli.fastfood.core.ports.driver.PedidoUseCase
import br.com.felixgilioli.fastfood.core.ports.driver.ProdutoUseCase
import br.com.felixgilioli.fastfood.core.usecases.ClienteUseCaseImpl
import br.com.felixgilioli.fastfood.core.usecases.PedidoUseCaseImpl
import br.com.felixgilioli.fastfood.core.usecases.ProdutoUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
        produtoRepository: ProdutoRepository
    ): PedidoUseCase {
        return PedidoUseCaseImpl(clienteRepository, pedidoRepository, produtoRepository)
    }

    @Bean
    fun produtoUseCase(produtoRepository: ProdutoRepository): ProdutoUseCase {
        return ProdutoUseCaseImpl(produtoRepository)
    }
}