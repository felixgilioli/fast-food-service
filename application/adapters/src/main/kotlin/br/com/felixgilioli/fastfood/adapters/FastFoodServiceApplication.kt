package br.com.felixgilioli.fastfood.adapters

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FastFoodServiceApplication

fun main(args: Array<String>) {
	runApplication<FastFoodServiceApplication>(*args)
}
