package com.gufeczek.pokecompanion.controller

import com.gufeczek.pokecompanion.core.CuratedPage
import com.gufeczek.pokecompanion.service.PokemonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonController(
    private val pokemonService: PokemonService
) {
    @GetMapping("/pokemon")
    fun getPokemonById(
        @RequestParam("limit") limit: Int,
        @RequestParam("offset") offset: Int
    ): CuratedPage<PokemonDto> {
        return pokemonService.getPokemonDtoPage(limit, offset)
    }


}