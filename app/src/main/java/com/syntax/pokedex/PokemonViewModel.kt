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
import com.syntax.pokedex.data.model.TypeRessource
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

    var counter = repository.count

    val maxCount = repository.maxCount

    private val _types = MutableLiveData<List<TypeRessource>>()
    val types: LiveData<List<TypeRessource>>
        get() = _types

    init {
        _types.value = repository.loadTypeRessources()
    }

    fun getAllPokemon(){
        viewModelScope.launch {
            repository.getPokemonsFromDatabase()
        }
    }

    fun getFavorites(){
        viewModelScope.launch {
            repository.getFavorites()
        }
    }

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

    fun loadPokeTypes(){
        viewModelScope.launch{
            try {
                repository.loadTypeRessources()
            }catch (e: Exception){
                Log.e(TAG, "Error loading type list data from repository")
            }
        }
    }


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

    fun addToFavorites(pokemonName: String){
        viewModelScope.launch {
            repository.addToFavorites(pokemonName)
        }
    }

    fun removeFavorite(pokemonName: String){
        viewModelScope.launch {
            repository.removeFavorite(pokemonName)
        }
    }

    fun getPokemonByType(type: String){
        viewModelScope.launch {
            repository.getPokemonByType(type)
        }
    }



}
