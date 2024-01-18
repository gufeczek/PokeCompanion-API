package com.gufeczek.pokecompanion.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "pokemon_type")
class PokemonType (
    @Id
    @Column(name = "pokemon_id")
    var pokemonId: Int,

    @Column(name = "type_id")
    var typeId: Int,

    @Column(name = "slot")
    var slot: Int
)