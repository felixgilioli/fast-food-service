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

## Como executar a aplicação via Docker

### Requisitos

- Docker instalado
- Docker Compose instalado

1. Baixe o docker compose do projeto disponível no repositório:
   ```sh
   https://github.com/felixgilioli/fast-food-service/blob/main/demo/docker-compose.yml

2. Baixe o arquivo secrets.env no mesmo diretório do docker-compose


3. Execute o comando abaixo para subir o banco de dados e a aplicação:
   ```sh
   docker-compose --env-file secrets.env up -d

## Como executar localmente

### Requisitos

- Java 21 instalado
- Docker instalado
- Docker Compose instalado

1. Clone o repositório:
   ```sh
   https://github.com/felixgilioli/fast-food-service.git

2. Suba o banco de dados com Docker Compose:
   ```sh
   cd fastfood/local
   docker-compose up -d

3. Volte para o diretório raiz do projeto:
   ```sh
   cd ..

4. Execute o projeto informando as variáveis de ambiente necessárias:

   #### Linux / macOS

   ```bash
   MERCADO_PAGO_ACCESS_TOKEN=TEST-xxx
   ./gradlew bootRun --args='--spring.profiles.active=local'
   ```

   #### Windows (PowerShell)
   
   ```bash
   $env:MERCADO_PAGO_ACCESS_TOKEN="xxx"
   ./gradlew bootRun --args='--spring.profiles.active=local'
   ```