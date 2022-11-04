package com.syntax.pokedex.data.model

import com.syntax.pokedex.data.model.pokemon.PokemonList

data class DataSource(
    val results: List<PokemonList>
)
