package br.com.felixgilioli.fastfood.application.events

interface EventListener<T : Event> {

    fun onEvent(event: T)
}