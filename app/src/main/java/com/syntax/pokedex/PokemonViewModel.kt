package com.syntax.pokedex

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.syntax.pokedex.data.Repository
import com.syntax.pokedex.data.remote.PokeApi
import kotlinx.coroutines.launch

const val TAG = "PokemonViewModel"

enum class ApiStatus { LOADING, DONE, ERROR }

class PokemonViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(PokeApi)
    val pokeList = repository.pokemons

    private val _loading = MutableLiveData<ApiStatus>()
    val loading: LiveData<ApiStatus>
        get() = _loading


    fun loadPokeArtwork(name: String){
        viewModelScope.launch {
            try {
                _loading.value = ApiStatus.LOADING
                repository.loadPokemonArtwork(name)
                _loading.value = ApiStatus.DONE
            }catch (e: Exception){
                Log.e(TAG, "Error loading artwork data from API: $e")
            }
        }
    }

    fun loadPokeList() {
        viewModelScope.launch {
            try {
                _loading.value = ApiStatus.LOADING
                repository.loadPokemons()
                _loading.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.e(TAG, "Error loading list data from API: $e")
                _loading.value = ApiStatus.ERROR
            }
        }
    }

    fun loadPokeDetails(name: String){
        viewModelScope.launch {
            try {
                _loading.value = ApiStatus.LOADING
                repository.loadPokemonDetails(name)
                _loading.value = ApiStatus.DONE
            }catch (e: Exception){
                Log.e(TAG, "Error loading detail data from API: $e")
            }
        }
    }
}