# FastFood

Sistema de gerenciamento de pedidos para uma lanchonete, desenvolvido em Kotlin com Spring Boot.

## Tecnologias Utilizadas

- Kotlin
- Java
- Spring Boot
- Gradle
- SQL
- Mercado Pago (integração de pagamentos)

## Funcionalidades

- Cadastro e gerenciamento de clientes
- Cadastro e gerenciamento de produtos e categorias
- Criação e confirmação de pedidos
- Processamento de pagamentos via Mercado Pago
- Acompanhamento do status dos pedidos (criado, confirmado, em preparação, pronto, finalizado)
- Eventos e listeners para atualização de status e notificações

## Como Executar

1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/fastfood.git

2. Acesse a pasta do projeto:
   ```sh
   cd fastfood

3. Configure as variáveis de ambiente necessárias (ex: tokens do Mercado Pago, configurações do banco de dados).

4. Execute o projeto:
    ```sh
    ./gradlew bootRun