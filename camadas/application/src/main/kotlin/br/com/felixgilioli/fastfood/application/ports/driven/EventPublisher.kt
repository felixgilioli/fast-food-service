package br.com.felixgilioli.fastfood.application.ports.driven

import br.com.felixgilioli.fastfood.application.events.Event

interface EventPublisher {

    fun publish(event: Event)
}