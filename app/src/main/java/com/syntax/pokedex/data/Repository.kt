package com.syntax.pokedex.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syntax.pokedex.R
import com.syntax.pokedex.data.local.PokeDatabase
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.model.TypeRessource
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
            getPokemonsFromDatabase()
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
                false,
                pokemon.stats[0].base_stat,
                pokemon.stats[1].base_stat,
                pokemon.stats[2].base_stat,
                pokemon.stats[3].base_stat,
                pokemon.stats[4].base_stat,
                pokemon.stats[5].base_stat

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
            "ich habe heute leider kein foto fuer dich",
            "SCHMATZ",
            0,
            131,
            "rot",
            null,
            false,
            1,
            2,
            3,
            4,
            5,
            6
        )
    }

    suspend fun getPokemonsFromDatabase() {
        _pokemonList.value =  database.pokeDatabaseDao.getAll()
    }


    suspend fun searchPokemonByName(pokemonName: String) {
        val result = database.pokeDatabaseDao.searchPokemon(pokemonName)
        _pokemonByName.value = result
    }

    suspend fun addToFavorites(pokemonName: String){
        database.pokeDatabaseDao.setFavorite(pokemonName, true)
    }

    suspend fun removeFavorite(pokemonName: String){
        database.pokeDatabaseDao.setFavorite(pokemonName, false)
    }


    suspend fun getPokemonByType(primaryType: String){
      _pokemonList.value =  database.pokeDatabaseDao.getByPrimarytype(primaryType)
    }

    suspend fun getFavorites(){
        _pokemonList.value = database.pokeDatabaseDao.getFavorites()
    }


    fun loadTypeRessources():List<TypeRessource>{
        return listOf(
            TypeRessource(
                "fire",
                R.drawable.fire
            ),
            TypeRessource(
                "grass",
                R.drawable.grass
            ),
            TypeRessource(
                "water",
                R.drawable.water
            ),
            TypeRessource(
                "bug",
                R.drawable.bug
            ),
            TypeRessource(
                "poison",
                R.drawable.poison
            ),
            TypeRessource(
                "psychic",
                R.drawable.psychic
            ),
            TypeRessource(
                "electric",
                R.drawable.electric
            ),
            TypeRessource(
                "fighting",
                R.drawable.fighting
            ),
            TypeRessource(
                "dragon",
                R.drawable.dragon
            ),
            TypeRessource(
                "dark" ,
                R.drawable.dark
            ),
            TypeRessource(
                "normal",
                R.drawable.normal
            ),
            TypeRessource(
                "fairy" ,
                R.drawable.fairy
            ),
            TypeRessource(
                "flying",
                R.drawable.flying
            ),
            TypeRessource(
                "steel",
                R.drawable.steel
            ),
            TypeRessource(
                "ice",
                R.drawable.ice
            ),
            TypeRessource(
                "rock",
                R.drawable.rock
            ),
            TypeRessource(
                "ghost",
                R.drawable.ghost
            )
        )
    }
}




