package com.gufeczek.pokecompanion.exception

import java.time.LocalDateTime
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ResponseExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleBadRequestException(e: Exception, request: WebRequest?): ResponseEntity<ApiException> {
        val body = ApiException(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "BAD_REQUEST",
            message = e.message.toString(),
            timeStamp = LocalDateTime.now().toString()
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}