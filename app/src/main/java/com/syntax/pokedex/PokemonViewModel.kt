package com.syntax.pokedex

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.syntax.pokedex.data.Repository
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.local.getDatabase
import com.syntax.pokedex.data.remote.PokeApi
import kotlinx.coroutines.launch

const val TAG = "PokemonViewModel"

enum class ApiStatus { LOADING, DONE, ERROR }

class PokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)

    private val repository = Repository(PokeApi, database)

    private val _loading = MutableLiveData<ApiStatus>()
    val loading: LiveData<ApiStatus>
        get() = _loading

    val pokemon = repository.pokemonList
    val pokemonByName = repository.pokemonByName


    private val _pokemonDetails = MutableLiveData<DatabasePokemon>()
    val pokemonDetails: LiveData<DatabasePokemon>
        get() =  _pokemonDetails


    fun loadPokeList() {
        viewModelScope.launch {
            try {
                _loading.value = ApiStatus.LOADING
                repository.getPokemonData()
                _loading.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.e(TAG, "Error loading list data from API: $e")
                _loading.value = ApiStatus.ERROR
            }
        }
    }

    // Todo api call machen! zB getTypeById

    fun loadPokeDetails(name: String){
        viewModelScope.launch {
            _pokemonDetails.value = repository.getPokemonByName(name)
        }
    }

    fun searchPokemon(name: String){
        viewModelScope.launch {
            repository.searchPokemonByName(name)
        }
    }


}
