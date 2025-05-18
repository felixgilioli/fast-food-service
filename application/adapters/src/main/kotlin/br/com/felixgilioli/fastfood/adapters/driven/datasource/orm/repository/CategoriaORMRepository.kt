package br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.repository

import br.com.felixgilioli.fastfood.adapters.driven.datasource.orm.CategoriaORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoriaORMRepository : JpaRepository<CategoriaORM, UUID>