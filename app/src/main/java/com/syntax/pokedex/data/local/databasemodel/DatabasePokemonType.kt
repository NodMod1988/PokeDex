package com.syntax.pokedex.data.local.databasemodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabasePokemonType(

    @PrimaryKey(autoGenerate = true)
    var typeId: Int,
    val type: String
)
