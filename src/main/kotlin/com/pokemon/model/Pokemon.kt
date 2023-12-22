package com.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
class Pokemon(
    val id: Int,
    val name: String,
    val url: String)

