package com.syntax.pokedex.data.local.databasemodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class DatabasePokemon(

    @PrimaryKey(autoGenerate = true)
    var pokeId: Int,
    val picture: String,
    val name: String,
    val weight: Int,
    val height: Int,
    val primaryType: String = "",
    val secundaryType: String? = null,
    var isFavorite: Boolean = false,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)
