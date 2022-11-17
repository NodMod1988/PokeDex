package com.syntax.pokedex.data.local.databasemodel.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemonType

data class TypeWithPokemon(

    @Embedded val type: DatabasePokemonType,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "pokeId",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val pokemons: List<DatabasePokemon>
)
