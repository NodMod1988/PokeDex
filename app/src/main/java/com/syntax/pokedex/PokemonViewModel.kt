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

    val details = repository.pokemons

    fun load() {
        viewModelScope.launch {
            try {
                _loading.value = ApiStatus.LOADING
                repository.loadPokemons()
                _loading.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.e(TAG, "Error loading data from API: $e")
                _loading.value = ApiStatus.ERROR
            }
        }
    }
}