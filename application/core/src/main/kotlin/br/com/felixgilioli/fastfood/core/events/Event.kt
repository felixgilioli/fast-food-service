package br.com.felixgilioli.fastfood.core.events

import java.time.LocalDateTime

open class Event(
    val occurredAt: LocalDateTime = LocalDateTime.now()
)