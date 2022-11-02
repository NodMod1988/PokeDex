package com.syntax.pokedex.data.model.pokemon

import com.squareup.moshi.Json

data class Other (

    @Json(name = "official-artwork")
    val officialArtwork: Artwork
        )
