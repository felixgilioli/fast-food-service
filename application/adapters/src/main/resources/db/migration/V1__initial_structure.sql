CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabela de cliente
CREATE TABLE IF NOT EXISTS cliente (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_completo VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL
);

-- Tabela de categoria
CREATE TABLE IF NOT EXISTS categoria (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    descricao VARCHAR(150) NOT NULL
);

-- Tabela de produto
CREATE TABLE IF NOT EXISTS produto (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(150) NOT NULL,
    categoria_id UUID NOT NULL REFERENCES categoria(id),
    preco DECIMAL(10,2) NOT NULL,
    descricao TEXT
);

-- Tabela de pedido
CREATE TABLE IF NOT EXISTS pedido (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    data_inicio TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    data_fim TIMESTAMP WITH TIME ZONE,
    cliente_nome VARCHAR(255) NOT NULL,
    cliente_id UUID REFERENCES cliente(id),
    total DECIMAL(10,2)
);

-- Tabela de itens do pedido
CREATE TABLE IF NOT EXISTS pedido_item (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id UUID NOT NULL REFERENCES pedido(id) ON DELETE CASCADE,
    produto_id UUID NOT NULL REFERENCES produto(id),
    quantidade INT NOT NULL CHECK (quantidade > 0),
    preco_unitario DECIMAL(10,2) NOT NULL
);

-- Tabela de pagamento
CREATE TABLE IF NOT EXISTS pagamento (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id UUID NOT NULL REFERENCES pedido(id) ON DELETE CASCADE,
    valor DECIMAL(10,2) NOT NULL,
    data TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    status VARCHAR(50) NOT NULL,
    link VARCHAR(255) NOT NULL
);