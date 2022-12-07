package com.syntax.pokedex.data.model.pokemonSpecies

import com.squareup.moshi.Json

data class PokemonSpecies(


    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>?

)