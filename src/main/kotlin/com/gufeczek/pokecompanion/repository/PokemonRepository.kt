package com.gufeczek.pokecompanion.repository

import com.gufeczek.pokecompanion.model.Pokemon
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface PokemonRepository : PagingAndSortingRepository<Pokemon, Int> {
    fun findByNameContaining(query: String, pageable: Pageable): Page<Pokemon>
}
