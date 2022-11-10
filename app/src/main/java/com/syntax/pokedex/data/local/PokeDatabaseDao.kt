package com.syntax.pokedex.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syntax.pokedex.data.model.PokemonListItem

@Dao
interface PokeDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList:List<PokemonListItem>)

    @Query("SELECT * from PokemonListItem")
    fun getAll(): LiveData<List<PokemonListItem>>

}