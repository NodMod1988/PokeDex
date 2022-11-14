package com.syntax.pokedex.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.model.PokemonListItem
import com.syntax.pokedex.data.model.pokemon.Pokemon

@Dao
interface PokeDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon(pokemonList:List<DatabasePokemon>)

    @Query("SELECT * from DatabasePokemon")
    fun getAll(): LiveData<List<DatabasePokemon>>

    @Query("SELECT * FROM DatabasePokemon WHERE name = :pokemonName")
    suspend fun getPokemonDetailsByName(pokemonName: String): DatabasePokemon

}