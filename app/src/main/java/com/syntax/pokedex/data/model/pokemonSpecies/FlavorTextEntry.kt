package com.syntax.pokedex.data.model.pokemonSpecies

import com.squareup.moshi.Json

data class FlavorTextEntry(
    @Json(name = "flavor_text")
    val flavorText: String?,
    val language: Language?

)