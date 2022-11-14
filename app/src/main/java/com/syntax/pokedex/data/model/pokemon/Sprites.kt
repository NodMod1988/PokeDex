package com.syntax.pokedex.data.model.pokemon

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


data class Sprites (

    val other: Other
    )
