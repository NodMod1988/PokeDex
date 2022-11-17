package com.syntax.pokedex.data.local.databasemodel.relations

import androidx.room.Entity


@Entity(primaryKeys = ["pokeId", "typeId"])
data class PokemonTypeCrossRef(

    val pokeId: Int,
    val typeId: Int
)
