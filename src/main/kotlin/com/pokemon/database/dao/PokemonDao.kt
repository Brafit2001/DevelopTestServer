package com.pokemon.database.dao

import com.pokemon.model.Pokemon
import com.pokemon.model.PostPokemonBody
import com.pokemon.model.PutPokemonBody
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


object Pokemons : Table("pokemons"), PokemonDao{
    val id = integer("id").autoIncrement()
    var name = varchar("name", 79)
    var url = varchar("url", 200)

    override val primaryKey = PrimaryKey(id)

    override fun getAllPokemons() : List<Pokemon> {
        return selectAll().map(::resultRowToPokemon)
    }

    override fun getPokemonById(pokemonId: Int): Pokemon?{
        return select {id eq pokemonId }
            .map(::resultRowToPokemon)
            .singleOrNull()
    }

    override fun postPokemon(postPokemon: PostPokemonBody): Pokemon? {
        val insertStatement = insert {
            it[name] = postPokemon.name
            it[url] = postPokemon.url

        }
        return insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPokemon)
    }

    override fun deletePokemon(pokemonId: Int): Boolean {
        return deleteWhere { id eq pokemonId } > 0
    }

    override fun putPokemon(pokemonId: Int, putPokemon: PutPokemonBody): Pokemon? {
        update({ id eq pokemonId }) {
            it[name] = putPokemon.name
            it[url] = putPokemon.url
        } > 0
        return getPokemonById(pokemonId)
    }
}

private fun resultRowToPokemon(row: ResultRow) =
    Pokemon(
        id = row[Pokemons.id],
        name = row[Pokemons.name],
        url = row[Pokemons.url],
    )

interface PokemonDao {
    fun getPokemonById(pokemonId: Int): Pokemon?
    fun getAllPokemons(): List<Pokemon>
    fun postPokemon(postPokemon: PostPokemonBody): Pokemon?
    fun deletePokemon(pokemonId: Int): Boolean
    fun putPokemon(pokemonId: Int, putPokemon: PutPokemonBody): Pokemon?
}