package br.com.felixgilioli.fastfood.infrastructure

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
	info = Info(
		title = "FastFood Service API",
		version = "v1",
		description = "API para gerenciamento do serviço FastFood"
	)
)
@SpringBootApplication
class FastFoodServiceApplication

fun main(args: Array<String>) {
	runApplication<FastFoodServiceApplication>(*args)
}
