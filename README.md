# FastFood

A aplicação é um sistema de autoatendimento para uma lanchonete em expansão, desenvolvido para otimizar o controle de pedidos, reduzir erros no atendimento e melhorar a experiência do cliente. Ela permite que os usuários façam pedidos personalizados (lanche, acompanhamento e bebida), realizem pagamentos via QR Code do Mercado Pago e acompanhem o status do pedido. O sistema também oferece API's para gestão de produtos, categorias, clientes e pedidos.

## Funcionalidades

- Cadastro e gerenciamento de clientes
- Cadastro e gerenciamento de produtos e categorias
- Criação e confirmação de pedidos
- Processamento de pagamentos via Mercado Pago
- Acompanhamento do status dos pedidos (criado, confirmado, em preparação, pronto, finalizado)
- Eventos e listeners para atualização de status e notificações
- Retirada do pedido pelo cliente

## Arquitetura do Projeto

### Tecnologias Utilizadas

- Kotlin
- Spring Boot
- Gradle
- Clean Architecture
- JUnit 5 e MockK (testes unitários)
- Swagger (documentação da API)
- Mercado Pago (integração de pagamentos)
- PostgreSQL / RDS
- Docker e Docker Compose
- Kubernetes / EKS
- GitHub Actions (CI/CD)

### Diagrama de sequência da aplicação

![Diagrama de sequencia da aplicação](docs/img/diagrama-sequencia.png)

### Visão geral da arquitetura

![Visão geral da arquitetura](docs/img/visao-geral-arquitetura.png)

## Como executar a aplicação via Docker

### Requisitos

- Docker instalado
- Docker Compose instalado

### Passo a passo

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

### Passo a passo

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