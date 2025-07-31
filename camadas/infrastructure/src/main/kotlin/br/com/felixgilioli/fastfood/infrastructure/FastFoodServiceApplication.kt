package br.com.felixgilioli.fastfood.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FastFoodServiceApplication

fun main(args: Array<String>) {
	runApplication<FastFoodServiceApplication>(*args)
}
