package com.syntax.pokedex.data.model.pokemon

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


data class Other (


    @Json(name = "official-artwork")
    val officialArtwork: Artwork
        )
