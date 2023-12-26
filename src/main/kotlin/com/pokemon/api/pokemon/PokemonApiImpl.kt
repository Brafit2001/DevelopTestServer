package com.pokemon.api.pokemon

import com.pokemon.database.dao.PokemonDao
import com.pokemon.model.Pokemon
import com.pokemon.model.PostPokemonBody
import com.pokemon.model.PutPokemonBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object PokemonApiImpl : PokemonApi, KoinComponent {

    private val pokemonDao by inject<PokemonDao>()
    override suspend fun getAllPokemons(): List<Pokemon> {
        return pokemonDao.getAllPokemons()
    }

    override suspend fun getPokemonById(id: Int): Pokemon?{
        return pokemonDao.getPokemonById(id)
    }

    override suspend fun createPokemon(postPokemon: PostPokemonBody): Pokemon? {
        return pokemonDao.postPokemon(postPokemon)
    }

    override suspend fun editPokemon(id: Int, putPokemon: PutPokemonBody): Pokemon? {
        return pokemonDao.putPokemon(id, putPokemon)
    }

    override suspend fun deletePokemon(id: Int): Boolean {
        return pokemonDao.deletePokemon(id)
    }
}
