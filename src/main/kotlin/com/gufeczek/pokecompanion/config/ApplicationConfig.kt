package com.gufeczek.pokecompanion.config

import com.gufeczek.pokecompanion.exception.QueryParamInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ApplicationConfig(
    private val queryParamInterceptor: QueryParamInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(queryParamInterceptor)
    }
}