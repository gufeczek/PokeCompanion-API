package com.gufeczek.pokecompanion.repository

import com.gufeczek.pokecompanion.model.PokemonType
import com.gufeczek.pokecompanion.model.Type
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PokemonTypeRepository : JpaRepository<PokemonType, Int> {
    @Query("SELECT t FROM Type t WHERE t.id = (SELECT p.typeId FROM PokemonType p WHERE " +
            "p.pokemonId = :pokemonId AND p.slot = 1)")
    fun findPrimaryTypeByPokemonId(@Param("pokemonId") pokemonId: Int): Type?
}