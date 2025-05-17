package br.com.felixgilioli.fastfood.core.events

interface EventListener<T : Event> {

    fun onEvent(event: T)
}