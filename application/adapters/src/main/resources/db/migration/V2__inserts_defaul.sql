INSERT INTO categoria (descricao) VALUES
    ('Hambúrgueres'),
    ('Bebidas'),
    ('Acompanhamentos'),
    ('Sobremesas');

INSERT INTO produto (nome, categoria_id, preco, descricao) VALUES
    (
       'X-Burger',
       (SELECT id FROM categoria WHERE descricao = 'Hambúrgueres'),
       17.50,
       'Hambúrguer bovino, queijo prato, bacon, cebola caramelizada.'
    ),
    (
       'X-Salada',
       (SELECT id FROM categoria WHERE descricao = 'Hambúrgueres'),
       16.90,
       'Hambúrguer bovino, queijo, alface, tomate, maionese da casa.'
    ),
    (
       'Suco de Laranja 300ml',
       (SELECT id FROM categoria WHERE descricao = 'Bebidas'),
       7.00,
       'Suco natural de laranja, sem adição de açúcar.'
    ),
    (
       'Refrigerante Guaraná Lata 350ml',
       (SELECT id FROM categoria WHERE descricao = 'Bebidas'),
       6.00,
       'Refrigerante Guaraná Antarctica, 350ml.'
    ),
    (
       'Chocolate Milkshake',
       (SELECT id FROM categoria WHERE descricao = 'Bebidas'),
       12.00,
       'Milkshake de chocolate com chantilly.'
    ),
    (
       'Onion Rings',
       (SELECT id FROM categoria WHERE descricao = 'Acompanhamentos'),
       10.50,
       'Anéis de cebola empanados.'
    ),
    (
       'Batata Rústica',
       (SELECT id FROM categoria WHERE descricao = 'Acompanhamentos'),
       11.90,
       'Batatas cortadas com casca, temperadas e assadas.'
    ),
    (
       'Chicken Nuggets',
       (SELECT id FROM categoria WHERE descricao = 'Acompanhamentos'),
       14.00,
       'Porção de nuggets de frango com molho agridoce.'
    ),
    (
       'Salada Grega',
       (SELECT id FROM categoria WHERE descricao = 'Acompanhamentos'),
       8.00,
       'Alface, tomate, pepino, azeitonas e queijo feta.'
    ),
    (
       'Brownie',
       (SELECT id FROM categoria WHERE descricao = 'Sobremesas'),
       9.50,
       'Brownie de chocolate com nozes.'
    ),
    (
       'Mousse de Maracujá',
       (SELECT id FROM categoria WHERE descricao = 'Sobremesas'),
       7.50,
       'Mousse de maracujá com calda de fruta.'
    ),
    (
       'Pudim de Leite',
       (SELECT id FROM categoria WHERE descricao = 'Sobremesas'),
       8.00,
       'Pudim de leite condensado com calda de caramelo.'
    );