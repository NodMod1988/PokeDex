package com.syntax.pokedex.data.local.databasemodel.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemonType


data class PokemonWithType(

    @Embedded val pokemon: DatabasePokemon,
    @Relation(
        parentColumn = "pokeId",
        entityColumn = "typeId",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val types: List<DatabasePokemonType>
)
