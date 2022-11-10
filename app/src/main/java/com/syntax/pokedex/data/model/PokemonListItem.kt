package com.syntax.pokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PokemonListItem(

    @PrimaryKey
    var name: String,
    var url: String
)