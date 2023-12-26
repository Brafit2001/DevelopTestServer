package com.pokemon.modules.injection

import com.pokemon.modules.pokemon.PokemonController
import com.pokemon.modules.pokemon.PokemonControllerImp
import org.koin.dsl.module


object ControllersInjection {
    val koinBeans =  module {
        single<PokemonController> { PokemonControllerImp() }
    }

}