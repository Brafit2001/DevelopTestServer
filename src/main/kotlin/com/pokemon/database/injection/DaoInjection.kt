package com.pokemon.database.injection

import com.pokemon.database.dao.PokemonDao
import com.pokemon.database.dao.Pokemons
import com.pokemon.database.dao.UserDao
import com.pokemon.database.dao.Users
import org.koin.dsl.module

object DaoInjection {
    val koinBeans = module {
        single<PokemonDao> { Pokemons }
        single<UserDao> { Users }
    }
}