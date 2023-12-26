package com.pokemon.modules.pokemon

import com.pokemon.api.pokemon.PokemonApi
import com.pokemon.database.DatabaseProvider.dbQuery
import com.pokemon.model.Pokemon
import com.pokemon.model.PostPokemonBody
import com.pokemon.model.PutPokemonBody
import com.pokemon.statuspages.PokemonNotFound
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PokemonControllerImp: PokemonController, KoinComponent {

    private val pokemonApi by inject<PokemonApi>()

    override suspend fun findAll(): List<Pokemon> = dbQuery {
        pokemonApi.getAllPokemons()
    }

    override suspend fun searchPokemon(pokemonId: Int): Pokemon = dbQuery {
        pokemonApi.getPokemonById(pokemonId) ?: throw PokemonNotFound()
    }

    override suspend fun createPokemon(postPokemon: PostPokemonBody): Pokemon = dbQuery {
        pokemonApi.createPokemon(postPokemon) ?: throw UnknownError("Internal Error")
    }

    override suspend fun deletePokemon(pokemonId: Int) {
        dbQuery {
            pokemonApi.deletePokemon(pokemonId)
        }
    }

    override suspend fun updatePokemon(pokemonId: Int, putPokemon: PutPokemonBody): Pokemon = dbQuery{
        try {
            searchPokemon(pokemonId)
            pokemonApi.editPokemon(pokemonId, putPokemon) ?: throw UnknownError("Internal Error")
        }catch (e: PokemonNotFound){
            val postPokemon = PostPokemonBody(putPokemon.name, putPokemon.url)
            pokemonApi.createPokemon(postPokemon) ?: throw UnknownError("Internal Error")
        }

    }


}

interface PokemonController {
    suspend fun searchPokemon(pokemonId: Int): Pokemon
    suspend fun findAll(): List<Pokemon>
    suspend fun createPokemon(postPokemon: PostPokemonBody): Pokemon
    suspend fun deletePokemon(pokemonId: Int)
    suspend fun updatePokemon(pokemonId: Int, putPokemon: PutPokemonBody): Pokemon


}