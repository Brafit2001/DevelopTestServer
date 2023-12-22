package com.pokemon.api.pokemon

import com.pokemon.database.DatabaseProvider.dbQuery
import com.pokemon.database.dao.Pokemons
import com.pokemon.model.Pokemon
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PokemonApiImpl : PokemonApi {
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


}

val dao: PokemonApi = PokemonApiImpl().apply { runBlocking {} }