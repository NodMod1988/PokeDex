package com.syntax.pokedex.data.model

import com.syntax.pokedex.data.model.pokemon.PokemonList

data class DataSource(
    val count: Int,
    val result: List<PokemonList>
)
