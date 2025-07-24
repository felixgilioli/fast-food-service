package br.com.felixgilioli.fastfood.infrastructure.driven.event

import br.com.felixgilioli.fastfood.application.events.Event
import br.com.felixgilioli.fastfood.application.events.EventListener
import br.com.felixgilioli.fastfood.application.events.EventPublisher
import org.springframework.stereotype.Component
import java.lang.reflect.ParameterizedType

@Component
class EventPublisherImpl(private val listeners: List<EventListener<*>>) : EventPublisher {

    override fun publish(event: Event) {
        listeners.filter {
            it.javaClass.genericInterfaces.any { type ->
                (type as? ParameterizedType)?.actualTypeArguments?.firstOrNull() == event.javaClass
            }
        }.forEach { (it as EventListener<Event>).onEvent(event) }
    }
}