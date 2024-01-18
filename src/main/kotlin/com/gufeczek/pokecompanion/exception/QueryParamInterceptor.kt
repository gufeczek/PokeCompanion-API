package com.gufeczek.pokecompanion.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.gufeczek.pokecompanion.controller.dto.ErrorDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class QueryParamInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            val requestParams = request.parameterNames.toList()
            val methodParams = handler.methodParameters.toList()

            val requiredParams = methodParams
                .filter { it.getParameterAnnotation(RequestParam::class.java)?.required ?: false }
                .map { it.parameter.name }

            val optionalParams = methodParams
                .filter { it.getParameterAnnotation(RequestParam::class.java)?.required == false }
                .map { it.parameter.name }


            val missingRequiredParams = requiredParams.filter { it !in requestParams }
            val unknownParams = requestParams.filter { it !in requiredParams && it !in optionalParams }

            if (missingRequiredParams.isNotEmpty() || unknownParams.isNotEmpty()) {
                val errorMessage = StringBuilder()

                if (missingRequiredParams.isNotEmpty()) {
                    errorMessage.append("Missing required parameters: ${missingRequiredParams.joinToString(separator = ", ")}.")
                }
                if (unknownParams.isNotEmpty()) {
                    errorMessage.append("Unknown parameters: ${unknownParams.joinToString(separator = ", ")}.")
                }

                val errorDto = ErrorDto(
                    status = HttpStatus.BAD_REQUEST.value(),
                    error = "BAD_REQUEST",
                    message = errorMessage.toString(),
                    timeStamp = LocalDateTime.now().toString()
                )

                response.writer.write(ObjectMapper().writeValueAsString(errorDto))
                response.status = HttpStatus.BAD_REQUEST.value()
                response.contentType = "application/json"
                return false
            }
        }

        return super.preHandle(request, response, handler)
    }
}