package com.gufeczek.pokecompanion.service

import com.gufeczek.pokecompanion.controller.PokemonDto
import com.gufeczek.pokecompanion.core.CuratedPage
import com.gufeczek.pokecompanion.core.OffsetPageRequest
import com.gufeczek.pokecompanion.model.Pokemon
import com.gufeczek.pokecompanion.core.toCuratedPage
import com.gufeczek.pokecompanion.repository.PokemonPagingRepository
import org.springframework.core.io.ResourceLoader
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonRepository: PokemonPagingRepository,
    private val resourceLoader: ResourceLoader
) {
    fun getPokemonDtoPage(offset: Int, limit: Int): CuratedPage<PokemonDto> {
        val pokemonPage = getPokemonPage(offset, limit)
        val pokemonDtoList = pokemonPage.content.mapNotNull { pokemon ->
            getPokemonImage(pokemonId = pokemon.id)?.let { image ->
                PokemonDto(
                    id = pokemon.id,
                    name = pokemon.name,
                    image = image
                )
            }
        }

        return PageImpl(pokemonDtoList, pokemonPage.pageable, pokemonPage.totalElements).toCuratedPage()
    }


    private fun getPokemonPage(limit: Int, offset: Int): Page<Pokemon> {
        val pageable = OffsetPageRequest.of(limit, offset)
        return pokemonRepository.findAll(pageable)
    }

    private fun getPokemonImage(pokemonId: Int): ByteArray? {
        val pokemonPaddedId = pokemonId.toString().padStart(3, '0')
        val resource = resourceLoader.getResource("classpath:assets/pokemon/$pokemonPaddedId.png")
        if (resource.exists()) {
            return resource.inputStream.readAllBytes()
        }
        return null
    }
}
