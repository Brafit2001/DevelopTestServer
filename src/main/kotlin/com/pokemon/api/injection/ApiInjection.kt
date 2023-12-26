package com.pokemon.api.injection


import com.pokemon.api.pokemon.PokemonApiImpl
import com.pokemon.api.pokemon.PokemonApi
import org.koin.dsl.module

object ApiInjection {
    val koinBeans = module {
        single<PokemonApi> { PokemonApiImpl }
    }
}