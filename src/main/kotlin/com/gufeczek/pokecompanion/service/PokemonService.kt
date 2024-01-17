package com.gufeczek.pokecompanion.service

import com.gufeczek.pokecompanion.controller.dto.PokemonDto
import com.gufeczek.pokecompanion.core.CuratedPage
import com.gufeczek.pokecompanion.core.OffsetPageRequest
import com.gufeczek.pokecompanion.model.Pokemon
import com.gufeczek.pokecompanion.core.toCuratedPage
import com.gufeczek.pokecompanion.repository.PokemonRepository
import org.springframework.core.io.ResourceLoader
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonRepository: PokemonRepository,
    private val resourceLoader: ResourceLoader
) {
    fun getPokemonPage(substring: String, offset: Int, limit: Int): CuratedPage<PokemonDto> {
        val pageable = OffsetPageRequest.of(offset, limit, Sort.unsorted())
        val page = pokemonRepository.findByNameContaining(substring, pageable)

        val pokemons = page.content.mapNotNull { pokemon ->
            val image = getPokemonImage(pokemonId = pokemon.id)
            image?.let {
                PokemonDto(
                    id = pokemon.id,
                    name = pokemon.name,
                    image = it
                )
            }
        }
        val nextOffset = page.content.lastOrNull()?.id?.plus(1)

        return PageImpl(pokemons, page.pageable, page.totalElements).toCuratedPage(nextOffset)
    }

    private fun getPokemonImage(pokemonId: Int): ByteArray? {
        val pokemonPaddedId = pokemonId.toString().padStart(3, '0')
        val resourcePath = "classpath:assets/pokemon/$pokemonPaddedId.png"

        val resource = resourceLoader.getResource(resourcePath)
        if (resource.exists()) {
            return resource.inputStream.readAllBytes()
        }
        return null
    }

}
