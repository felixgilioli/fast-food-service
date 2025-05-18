package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm

import br.com.felixgilioli.fastfood.core.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.core.entities.Produto
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "produto")
data class ProdutoORM(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    val id: UUID? = null,

    @Column(name = "nome", nullable = false, length = 150)
    val nome: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    val categoria: CategoriaORM,

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    val preco: BigDecimal,

    @Column(name = "descricao")
    val descricao: String?
) {
    fun toDomain() = Produto(
        id = id,
        nome = nome,
        categoria = categoria.toDomain(),
        preco = preco,
        descricao = descricao
    )
}

fun Produto.toORM() = ProdutoORM(
    id = this.id,
    nome = this.nome,
    categoria = this.categoria.toORM(),
    preco = this.preco,
    descricao = this.descricao
)

fun ProdutoCommand.toORM(categoria: CategoriaORM) = ProdutoORM(
    id = this.id,
    nome = this.nome,
    categoria = categoria,
    preco = this.preco,
    descricao = this.descricao
)