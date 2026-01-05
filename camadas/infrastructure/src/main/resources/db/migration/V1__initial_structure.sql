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
    imagem_url TEXT,
    descricao TEXT
);

-- Tabela de pagamento
CREATE TABLE IF NOT EXISTS pagamento (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id VARCHAR(150) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    status VARCHAR(50) NOT NULL,
    link VARCHAR(255) NOT NULL
);