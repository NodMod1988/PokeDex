package com.syntax.pokedex.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon

@Dao
interface PokeDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon(pokemonList:List<DatabasePokemon>)

    @Query("SELECT * from DatabasePokemon")
    suspend fun getAll():List<DatabasePokemon>

    @Query("SELECT * FROM DatabasePokemon WHERE name = :pokemonName")
    suspend fun getPokemonDetailsByName(pokemonName: String): DatabasePokemon

    @Query("SELECT * FROM DatabasePokemon WHERE name LIKE :pokemonName || '%'")
    suspend fun searchPokemon(pokemonName: String): List<DatabasePokemon>

    @Query("SELECT (SELECT COUNT(*) FROM DatabasePokemon) == 0")
    suspend fun checkIsDbEmpty():Boolean

    @Query("SELECT COUNT (*) FROM DatabasePokemon")
    suspend fun countPokemons():Int

    @Query("SELECT * FROM DatabasePokemon WHERE isFavorite = 1")
    suspend fun getFavorites(): List<DatabasePokemon>

    @Query("UPDATE DatabasePokemon SET isFavorite = :isFavorite WHERE name = :pokemonName")
    suspend fun setFavorite(pokemonName: String, isFavorite: Boolean)

    @Query("SELECT * FROM DatabasePokemon WHERE primaryType = :primaryType")
    suspend fun getByPrimarytype(primaryType: String):List<DatabasePokemon>

    @Query("SELECT * FROM DatabasePokemon")
    fun selectAll(): LiveData<List<DatabasePokemon>>


}