package com.pokemon.api.pokemon

import com.pokemon.model.Pokemon

interface PokemonApi {
    suspend fun getAllPokemons(): List<Pokemon>
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun createPokemon(name: String, url: String): Pokemon?
    suspend fun editPokemon(id: Int, name: String, url: String): Boolean
    suspend fun deletePokemon(id: Int): Boolean
}