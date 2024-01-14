package com.gufeczek.pokecompanion.repository

import com.gufeczek.pokecompanion.core.PagingRepository
import com.gufeczek.pokecompanion.model.Pokemon

interface PokemonPagingRepository : PagingRepository<Pokemon, Int>
