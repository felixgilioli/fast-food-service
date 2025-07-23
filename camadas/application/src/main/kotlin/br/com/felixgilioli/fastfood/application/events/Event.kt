package br.com.felixgilioli.fastfood.application.events

import java.time.LocalDateTime

open class Event(
    val occurredAt: LocalDateTime = LocalDateTime.now()
)