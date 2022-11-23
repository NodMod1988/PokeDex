package com.syntax.pokedex.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syntax.pokedex.data.local.PokeDatabase
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.model.pokemon.Pokemon
import com.syntax.pokedex.data.remote.PokeApi

class Repository(private val api: PokeApi, private val database: PokeDatabase) {

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    private val _pokemonByName = MutableLiveData<List<DatabasePokemon>>()
    val pokemonByName: LiveData<List<DatabasePokemon>>
        get() = _pokemonByName

    private val _pokemonList = MutableLiveData<List<DatabasePokemon>>()
    val pokemonList: LiveData<List<DatabasePokemon>>
        get() = _pokemonList


    suspend fun getPokemonData() {

        var isEmpty = database.pokeDatabaseDao.checkIsDbEmpty()

        if (isEmpty) {
            Log.i("Repository", "Database is Empty")
            try {
                var response = api.retrofitservice.getPokemonList()
                var allPokemon: MutableList<Pokemon> = mutableListOf()
                for (result in response.results) {
                    try {
                        val pokemon = api.retrofitservice.getPokemon(result.name)
                        allPokemon.add(pokemon)
                    } catch (e: Exception) {
                        Log.e("Repository", "A Error Occured1: $e")
                    }
                }
                parsePokemon(allPokemon)
            } catch (e: Exception) {
                Log.e("Repository", "A Error Occured2: $e")
            }
        } else {
            // nimmt sämtliche daten aus der datenbank
            Log.i("Repository", "In the database")
            _pokemonList.postValue(getPokemonsFromDatabase()!!)
        }
    }


    suspend fun parsePokemon(allPokemon: MutableList<Pokemon>) {
        val newPokemonList = mutableListOf<DatabasePokemon>()
        for (pokemon in allPokemon) {
            val databasePokemon = DatabasePokemon(
                pokemon.id,
                pokemon.sprites.other.officialArtwork.front_default ?: " ",
                pokemon.name,
                pokemon.weight,
                pokemon.height,
                pokemon.types[0].type.name,
                if (pokemon.types.size>1) pokemon.types[1].type.name else null,


            )
            newPokemonList.add(databasePokemon)

        }
        database.pokeDatabaseDao.insertAllPokemon(newPokemonList)
        _pokemonList.postValue(newPokemonList)
    }

    suspend fun getPokemonByName(pokemonName: String): DatabasePokemon {
        try {

            return database.pokeDatabaseDao.getPokemonDetailsByName(pokemonName)

        } catch (e: Exception) {
            Log.e("Repository", "An error occured: $e")
        }
        return DatabasePokemon(
            1337,
            "ich habe heute kein foto fuer dich",
            "beim nächsten mal vielleicht",
            0,
            131,
            "rot",
            null,
        )
    }

    suspend fun getPokemonsFromDatabase(): List<DatabasePokemon> {
        return database.pokeDatabaseDao.getAll()
    }


    suspend fun searchPokemonByName(pokemonName: String) {
        val result = database.pokeDatabaseDao.searchPokemon(pokemonName)
        _pokemonByName.value = result
    }

}