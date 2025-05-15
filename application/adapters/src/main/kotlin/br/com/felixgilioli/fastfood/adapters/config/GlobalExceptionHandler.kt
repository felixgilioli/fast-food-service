package br.com.felixgilioli.fastfood.adapters.config

import br.com.felixgilioli.fastfood.core.exceptions.ClienteAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorResponse(val status: Int, val message: String)

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ClienteAlreadyExistsException::class)
    fun handleClienteAlreadyExists(ex: ClienteAlreadyExistsException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.CONFLICT.value(), ex.message ?: "Erro ao cadastrar cliente")
        return ResponseEntity(error, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.message ?: "Requisição inválida")
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message ?: "Erro interno no servidor")
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}