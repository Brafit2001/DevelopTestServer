package com.pokemon.database.dao

import com.pokemon.model.Pokemon
import org.jetbrains.exposed.sql.Table

object Pokemons : Table("pokemons"), PokemonDao{
    val id = integer("id").autoIncrement()
    var name = varchar("name", 79)
    var url = varchar("url", 200)

    override val primaryKey = PrimaryKey(id)
}

interface PokemonDao {

}