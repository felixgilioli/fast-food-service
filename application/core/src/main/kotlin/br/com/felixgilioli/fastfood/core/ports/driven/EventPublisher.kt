package br.com.felixgilioli.fastfood.core.ports.driven

import br.com.felixgilioli.fastfood.core.events.Event

interface EventPublisher {

    fun publish(event: Event)
}