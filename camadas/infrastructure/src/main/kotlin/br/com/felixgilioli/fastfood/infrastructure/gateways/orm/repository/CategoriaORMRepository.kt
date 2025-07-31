package br.com.felixgilioli.fastfood.infrastructure.gateways.orm.repository

import br.com.felixgilioli.fastfood.infrastructure.gateways.orm.CategoriaORM
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoriaORMRepository : JpaRepository<CategoriaORM, UUID>