INSERT INTO categoria (descricao) VALUES
    ('Hambúrgueres'),
    ('Bebidas'),
    ('Acompanhamentos'),
    ('Sobremesas');

INSERT INTO produto (nome, categoria_id, preco, imagem_url, descricao) VALUES
    (
       'X-Burger',
       (SELECT id FROM categoria WHERE descricao = 'Hambúrgueres'),
       17.50,
       'https://example.com/x-burger.jpg',
       'Hambúrguer bovino, queijo prato, bacon, cebola caramelizada.'
    ),
    (
       'X-Salada',
       (SELECT id FROM categoria WHERE descricao = 'Hambúrgueres'),
       16.90,
       'https://example.com/x-salada.jpg',
       'Hambúrguer bovino, queijo, alface, tomate, maionese da casa.'
    ),
    (
       'Suco de Laranja 300ml',
       (SELECT id FROM categoria WHERE descricao = 'Bebidas'),
       7.00,
       'https://example.com/suco-laranja.jpg',
       'Suco natural de laranja, sem adição de açúcar.'
    ),
    (
       'Refrigerante Guaraná Lata 350ml',
       (SELECT id FROM categoria WHERE descricao = 'Bebidas'),
       6.00,
       'https://example.com/refrigerante-lata.jpg',
       'Refrigerante Guaraná Antarctica, 350ml.'
    ),
    (
       'Chocolate Milkshake',
       (SELECT id FROM categoria WHERE descricao = 'Bebidas'),
       12.00,
       'https://example.com/milkshake-chocolate.jpg',
       'Milkshake de chocolate com chantilly.'
    ),
    (
       'Onion Rings',
       (SELECT id FROM categoria WHERE descricao = 'Acompanhamentos'),
       10.50,
       'https://example.com/onion-rings.jpg',
       'Anéis de cebola empanados.'
    ),
    (
       'Batata Rústica',
       (SELECT id FROM categoria WHERE descricao = 'Acompanhamentos'),
       11.90,
       'https://example.com/batata-rustica.jpg',
       'Batatas cortadas com casca, temperadas e assadas.'
    ),
    (
       'Chicken Nuggets',
       (SELECT id FROM categoria WHERE descricao = 'Acompanhamentos'),
       14.00,
       'https://example.com/chicken-nuggets.jpg',
       'Porção de nuggets de frango com molho agridoce.'
    ),
    (
       'Salada Grega',
       (SELECT id FROM categoria WHERE descricao = 'Acompanhamentos'),
       8.00,
       'https://example.com/salada-grega.jpg',
       'Alface, tomate, pepino, azeitonas e queijo feta.'
    ),
    (
       'Brownie',
       (SELECT id FROM categoria WHERE descricao = 'Sobremesas'),
       9.50,
       'https://example.com/brownie.jpg',
       'Brownie de chocolate com nozes.'
    ),
    (
       'Mousse de Maracujá',
       (SELECT id FROM categoria WHERE descricao = 'Sobremesas'),
       7.50,
       'https://example.com/mousse-maracuja.jpg',
       'Mousse de maracujá com calda de fruta.'
    ),
    (
       'Pudim de Leite',
       (SELECT id FROM categoria WHERE descricao = 'Sobremesas'),
       8.00,
       'https://example.com/pudim-leite.jpg',
       'Pudim de leite condensado com calda de caramelo.'
    );