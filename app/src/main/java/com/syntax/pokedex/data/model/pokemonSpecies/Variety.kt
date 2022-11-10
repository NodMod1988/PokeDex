package com.syntax.pokedex.data.model.pokemon

import com.syntax.pokedex.data.model.PokemonListItem



data class Variety(
    val is_default: Boolean,
    val pokemon: PokemonListItem
)