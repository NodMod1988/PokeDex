package com.syntax.pokedex.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.syntax.pokedex.data.model.DataSource
import com.syntax.pokedex.data.model.pokemon.Pokemon
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://pokeapi.co/api/v2/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PokeApiService {

    @GET("pokemon?limit=100000&offset=0")
    suspend fun getPokemonList():DataSource

    @GET("pokemon/{name}")
    suspend fun getPokemon(name: String): Pokemon
}

object RetroApi {
    val retrofitservice: PokeApiService by lazy { retrofit.create(PokeApiService::class.java) }
}