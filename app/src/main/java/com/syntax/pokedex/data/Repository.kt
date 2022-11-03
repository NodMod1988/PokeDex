package com.syntax.pokedex.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syntax.pokedex.data.model.pokemon.Other
import com.syntax.pokedex.data.model.pokemon.Pokemon
import com.syntax.pokedex.data.model.pokemon.PokemonList
import com.syntax.pokedex.data.remote.PokeApi

class Repository(private val api: PokeApi) {

    private val _pokemons = MutableLiveData<List<PokemonList>>()
    val pokemons: LiveData<List<PokemonList>>
        get() = _pokemons

    private val _pokemonPicture = MutableLiveData<Other>()
    val pokemonPicture: LiveData<Other>
        get() = _pokemonPicture

    suspend fun loadPokemons() {
        try {

            val response = PokeApi.retrofitservice.getPokemonList()
            _pokemons.value = response.result

        }catch (e:Exception){
            Log.e("Repository", e.message.toString())
        }

    }
}