package com.gufeczek.pokecompanion.controller

import com.gufeczek.pokecompanion.controller.dto.PokemonDto
import com.gufeczek.pokecompanion.core.paging.CuratedPage
import com.gufeczek.pokecompanion.service.PokemonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonController(
    private val pokemonService: PokemonService
) {
    @GetMapping("/pokemon")
    suspend fun getPokemonPage(
        @RequestParam(required = false) query: String?,
        @RequestParam offset: Int,
        @RequestParam limit: Int,
    ): CuratedPage<PokemonDto> {
        return pokemonService.getPokemonPage(
            query = query ?: "",
            offset = offset,
            limit = limit
        )
    }
}