package br.com.felixgilioli.fastfood.infrastructure.config

import br.com.felixgilioli.fastfood.application.gateways.ClienteGateway
import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import br.com.felixgilioli.fastfood.application.usecases.cliente.CadastrarClienteUseCase
import br.com.felixgilioli.fastfood.application.usecases.produto.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun cadastrarClienteUseCase(clienteGateway: ClienteGateway) = CadastrarClienteUseCase(clienteGateway)


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

}