package br.com.felixgilioli.fastfood.application.events

interface EventPublisher {

    fun publish(event: Event)
}