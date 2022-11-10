package com.syntax.pokedex.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syntax.pokedex.data.local.PokeDatabase
import com.syntax.pokedex.data.model.PokemonListItem
import com.syntax.pokedex.data.model.pokemon.Pokemon
import com.syntax.pokedex.data.remote.PokeApi

class Repository(private val api: PokeApi, private val database: PokeDatabase) {

    private val _pokemons = MutableLiveData<List<PokemonListItem>>()
    val pokemons: LiveData<List<PokemonListItem>>
        get() = _pokemons

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    private val _allPokemon = MutableLiveData<List<Pokemon>>()
    val allPokemon: LiveData<List<Pokemon>>
        get() = _allPokemon

    suspend fun loadPokemons() {
        try {
            var response = api.retrofitservice.getPokemonList()

            for(i in response.results.indices){
                response.results[i].url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${i+1}.png"
                response.results[i].name = response.results[i].name.capitalize()
            }

            _pokemons.value = response.results
            database.pokeDatabaseDao.insertAll(response.results)
        }catch (e:Exception){
            Log.e("Repository", e.message.toString())
        }

    }


    suspend fun loadPokemon(name: String){
        try {
            val response = api.retrofitservice.getPokemon(name.lowercase())
            _pokemon.value = response

        }catch (e:Exception){
            Log.e("Repository", e.message.toString())
        }
    }


    suspend fun loadAllPokeDetails(pokemonList:List<PokemonListItem>){
        val allPokemon:  MutableList<Pokemon> = mutableListOf()

        // Todo falls daten in der db, db holen ansonsten api ->
        for (pokemon in pokemonList){
            val poke = api.retrofitservice.getPokemon(pokemon.name.lowercase())
            // Todo daten in db spielen
            allPokemon.add(poke)
            println(pokemon.name + " loaded")
        }
        _allPokemon.value = allPokemon
    }

}