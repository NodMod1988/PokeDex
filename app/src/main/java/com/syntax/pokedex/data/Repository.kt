package com.syntax.pokedex.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import com.syntax.pokedex.data.local.PokeDatabase
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.model.PokemonListItem
import com.syntax.pokedex.data.model.pokemon.Pokemon
import com.syntax.pokedex.data.remote.PokeApi
import okhttp3.internal.threadName

class Repository(private val api: PokeApi, private val database: PokeDatabase) {

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    private val _pokemonByName = MutableLiveData<List<DatabasePokemon>>()
    val pokemonByName: LiveData<List<DatabasePokemon>>
        get() = _pokemonByName

    val pokemonList = database.pokeDatabaseDao.getAll()


    suspend fun getPokemonData() {
        try {
            var response = api.retrofitservice.getPokemonList()
            var allPokemon: MutableList<Pokemon> = mutableListOf()
            for (result in response.results) {
                try {
                    val pokemon = api.retrofitservice.getPokemon(result.name)
                    allPokemon.add(pokemon)
                } catch (e: Exception) {
                    Log.e("Repository", "A Error Occured: $e")
                }

            }
            parsePokemon(allPokemon)
        } catch (e: Exception) {
            Log.e("Repository", "A Error Occured: $e")
        }
    }

    suspend fun parsePokemon(allPokemon: MutableList<Pokemon>) {
        val newPokemonList = mutableListOf<DatabasePokemon>()
        for (pokemon in allPokemon) {
            val databasePokemon = DatabasePokemon(
                pokemon.id,
                pokemon.sprites.other.officialArtwork.front_default ?: " ",
                pokemon.name

            )
            newPokemonList.add(databasePokemon)

        }
        database.pokeDatabaseDao.insertAllPokemon(newPokemonList)
    }

    suspend fun getPokemonByName(pokemonName: String): DatabasePokemon {
        try {

            return database.pokeDatabaseDao.getPokemonDetailsByName(pokemonName)

        } catch (e: Exception) {
            Log.e("Repository", "An error occured: $e")
        }
        return DatabasePokemon(1337,"kein foto fuer dich","beim nächsten mal")
    }


    suspend fun searchPokemonByName(pokemonName: String){
        val result = database.pokeDatabaseDao.searchPokemon(pokemonName)
        _pokemonByName.value = result


    }

}