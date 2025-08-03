package br.com.felixgilioli.fastfood.infrastructure.repository

import br.com.felixgilioli.fastfood.infrastructure.orm.CategoriaORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoriaORMRepository : JpaRepository<CategoriaORM, UUID>