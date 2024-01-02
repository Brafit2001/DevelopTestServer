package com.pokemon.api.pokemon

import com.pokemon.model.Pokemon
import com.pokemon.model.PostPokemonBody
import com.pokemon.model.PutPokemonBody

interface PokemonApi {
    suspend fun getAllPokemons(): List<Pokemon>
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun getPokemonByName(name: String): Pokemon?
    suspend fun createPokemon(postPokemon: PostPokemonBody): Pokemon?
    suspend fun editPokemon(id: Int, putPokemon: PutPokemonBody): Pokemon?
    suspend fun deletePokemon(id: Int): Boolean
}