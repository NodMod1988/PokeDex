package com.syntax.pokedex.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syntax.pokedex.data.model.pokemon.Other
import com.syntax.pokedex.data.model.pokemon.PokemonList

class Repository {

    private val _pokemonPicture = MutableLiveData<Other>()
    val pokemonPicture: LiveData<Other>
        get() = _pokemonPicture
}