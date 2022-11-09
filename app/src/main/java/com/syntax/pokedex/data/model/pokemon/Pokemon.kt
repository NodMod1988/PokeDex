package com.syntax.pokedex.data.model.pokemon

data class Pokemon(

    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<Types>
)
