package com.exam.blogsearchapiapp.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

// 응답 status 타입, more exception 추후 추가할 것.
@ControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    data class ErrorResponseDto(
        val status: HttpStatus,
        val message: String? = "empty error message"
    )

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(exception: IllegalArgumentException): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity.ok(ErrorResponseDto(HttpStatus.BAD_REQUEST, exception.message))
    }

    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateException(exception: IllegalStateException): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity.ok(ErrorResponseDto(HttpStatus.BAD_REQUEST, exception.message))
    }

    @ExceptionHandler(Exception::class)
    fun unknownException(exception: Exception): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity.ok(ErrorResponseDto(HttpStatus.BAD_REQUEST, exception.message))
    }
}