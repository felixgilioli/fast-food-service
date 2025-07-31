package br.com.felixgilioli.fastfood.application.exceptions

class ClienteAlreadyExistsException(email: String) : RuntimeException("Cliente com email $email já cadastrado")