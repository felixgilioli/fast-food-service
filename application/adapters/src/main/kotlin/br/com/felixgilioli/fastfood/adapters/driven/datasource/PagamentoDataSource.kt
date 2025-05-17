package br.com.felixgilioli.fastfood.adapters.driven.datasource

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository.PagamentoORMRepository
import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.toOrm
import br.com.felixgilioli.fastfood.core.entities.Pagamento
import br.com.felixgilioli.fastfood.core.ports.driven.PagamentoRepository
import org.springframework.stereotype.Service

@Service
class PagamentoDataSource(private val pagamentoORMRepository: PagamentoORMRepository) : PagamentoRepository {

    override fun insert(pagamento: Pagamento) =
        pagamentoORMRepository.save(pagamento.toOrm()).toDomain()
}