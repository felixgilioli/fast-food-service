package br.com.felixgilioli.fastfood.infrastructure.gateways

import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.repository.CategoriaORMRepository
import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.repository.ProdutoORMRepository
import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.toORM
import br.com.felixgilioli.fastfood.application.commands.ProdutoCommand
import br.com.felixgilioli.fastfood.application.gateways.ProdutoGateway
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProdutoGatewayImpl(
    private val produtoORMRepository: ProdutoORMRepository,
    private val categoriaORMRepository: CategoriaORMRepository
) : ProdutoGateway {

    override fun findAll() = produtoORMRepository.findAll().map { it.toDomain() }

    override fun findAllById(produtoIds: List<UUID>) =
        produtoORMRepository.findAllById(produtoIds).map { it.toDomain() }

    override fun findById(produtoId: UUID) = produtoORMRepository.findByIdOrNull(produtoId)?.toDomain()

    override fun save(produto: ProdutoCommand) = categoriaORMRepository.findByIdOrNull(produto.categoriaId)
        ?.let { produtoORMRepository.save(produto.toORM(it)).toDomain() }
        ?: throw IllegalArgumentException("Categoria não encontrada")

    override fun findByCategoriaId(categoriaId: UUID) =
        produtoORMRepository.findByCategoriaId(categoriaId).map { it.toDomain() }
}