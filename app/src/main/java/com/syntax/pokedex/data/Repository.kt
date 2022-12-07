package com.syntax.pokedex.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syntax.pokedex.R
import com.syntax.pokedex.data.local.PokeDatabase
import com.syntax.pokedex.data.local.databasemodel.DatabasePokemon
import com.syntax.pokedex.data.model.TypeRessource
import com.syntax.pokedex.data.model.pokemon.Pokemon
import com.syntax.pokedex.data.model.pokemonSpecies.PokemonSpecies
import com.syntax.pokedex.data.remote.PokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int>
        get() = _count

    private val _maxCount = MutableLiveData<Int>()
    val maxCount: LiveData<Int>
        get() = _maxCount


    suspend fun getPokemonData() {

        var isEmpty = database.pokeDatabaseDao.checkIsDbEmpty()
        var countList: Int = 0
        if (isEmpty) {
            Log.i("Repository", "Database is Empty")
            try {
                var response = api.retrofitservice.getPokemonList()
                _maxCount.value = response.results.size

                for (result in response.results) {
                    try {
                        val pokemon = api.retrofitservice.getPokemon(result.name)
                        var description = "No Description"
                        try {
                            val pokemonDescription = api.retrofitservice.getPokemonDescription(result.name)
                            val germanDescriptions = pokemonDescription.flavorTextEntries?.filter {it -> it.language?.name == "de" }
                            if(germanDescriptions.isNullOrEmpty()){
                                break
                            }else{
                                description =  germanDescriptions.get(0).flavorText.toString()
                            }

                        }catch (e: Exception){
                            //Log.i("Repository", "No Description for this Pokemon")
                        }

                        parseSinglePokemon(pokemon, description)
                        countList += 1
                        _count.value = countList
                    } catch (e: Exception) {
                        Log.e("Repository", "A Error Occured1: $e")
                    }
                }
                _pokemonList.value = database.pokeDatabaseDao.getAll()
                //parsePokemon(allPokemon, pokemonDescriptionList)
            } catch (e: Exception) {
                Log.e("Repository", "A Error Occured2: $e")
            }
        } else {
            // nimmt sämtliche daten aus der datenbank
            Log.i("Repository", "In the database")
            getPokemonsFromDatabase()
        }
    }

    suspend fun parseSinglePokemon(pokemon: Pokemon, description: String){

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
            pokemon.stats[5].base_stat,
            description
        )
        database.pokeDatabaseDao.insertSinglePokemon(databasePokemon)
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
                6,
                "blablabla"
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




