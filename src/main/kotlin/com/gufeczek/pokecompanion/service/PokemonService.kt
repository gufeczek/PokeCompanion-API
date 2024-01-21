package com.gufeczek.pokecompanion.service

import com.gufeczek.pokecompanion.controller.dto.PokemonDto
import com.gufeczek.pokecompanion.core.paging.CuratedPage
import com.gufeczek.pokecompanion.core.paging.OffsetPageRequest
import com.gufeczek.pokecompanion.core.paging.toCuratedPage
import com.gufeczek.pokecompanion.model.Type
import com.gufeczek.pokecompanion.repository.PokemonRepository
import com.gufeczek.pokecompanion.repository.PokemonTypeRepository
import org.springframework.core.io.ResourceLoader
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonRepository: PokemonRepository,
    private val pokemonTypeRepository: PokemonTypeRepository,
    private val resourceLoader: ResourceLoader
) {
    fun getPokemonPage(query: String, offset: Int, limit: Int): CuratedPage<PokemonDto> {
        val pageable = OffsetPageRequest.of(offset, limit, Sort.unsorted())
        val page = pokemonRepository.findByNameContaining(query, pageable)

        val pokemons = page.content.mapNotNull { pokemon ->
            val image = getPokemonImage(pokemonId = pokemon.id)
            val primaryType = getPokemonPrimaryType(pokemon.id)?.name

            if (image != null && primaryType != null) {
                PokemonDto(
                    id = pokemon.id,
                    name = pokemon.name,
                    primaryType = primaryType,
                    image = image
                )
            } else {
                null
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

    private fun getPokemonPrimaryType(pokemonId: Int): Type? {
        return pokemonTypeRepository.findPrimaryTypeByPokemonId(pokemonId)
    }

}
