package br.com.felixgilioli.fastfood.core.exceptions

class ClienteAlreadyExistsException(email: String) : RuntimeException("Cliente com email $email já cadastrado")