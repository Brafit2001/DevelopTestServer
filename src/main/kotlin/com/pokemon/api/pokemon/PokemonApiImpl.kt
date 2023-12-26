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


    /*
    private fun resultRowToPokemon(row: ResultRow) =
        Pokemon(
            id = row[Pokemons.id],
            name = row[Pokemons.name],
            url = row[Pokemons.url],
    )

    override suspend fun getAllPokemons(): List<Pokemon> = dbQuery{
        println(::resultRowToPokemon)
        Pokemons.selectAll().map(::resultRowToPokemon)
    }

    override suspend fun getPokemonById(id: Int): Pokemon? = dbQuery{
        Pokemons
            .select { Pokemons.id eq id }
            .map(::resultRowToPokemon)
            .singleOrNull()
    }

    override suspend fun createPokemon(name: String, url: String): Pokemon? = dbQuery {
        val insertStatement = Pokemons.insert {
            it[Pokemons.name] = name
            it[Pokemons.url] = url

        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPokemon)
    }

    override suspend fun editPokemon(id: Int, name: String, url: String): Boolean = dbQuery {
        Pokemons.update({ Pokemons.id eq id }) {
            it[Pokemons.name] = name
            it[Pokemons.url] = url
        } > 0
    }

    override suspend fun deletePokemon(id: Int): Boolean = dbQuery{
        Pokemons.deleteWhere { Pokemons.id eq id } > 0
    }

     */


}

//val dao: PokemonApi = PokemonApiImpl().apply { runBlocking {} }