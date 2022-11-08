package com.syntax.pokedex.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.syntax.pokedex.data.model.pokemon.PokemonList

@Dao
interface PokeDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList:List<PokemonList>)

    @Query("SELECT * from PokemonList")
    fun getAll(): LiveData<List<PokemonList>>

}