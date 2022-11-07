package com.syntax.pokedex.data.model.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonList (

    @PrimaryKey
    val name: String,

    val url: String

    )


