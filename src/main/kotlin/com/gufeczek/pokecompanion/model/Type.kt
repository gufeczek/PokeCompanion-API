package com.gufeczek.pokecompanion.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "type")
class Type (
    @Id
    @Column
    var id: Int,

    @Column(name = "identifier")
    var name: String
)