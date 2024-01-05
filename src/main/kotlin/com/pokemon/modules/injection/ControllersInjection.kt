package com.pokemon.modules.injection

import com.pokemon.modules.pokemon.PokemonController
import com.pokemon.modules.pokemon.PokemonControllerImp
import com.user.modules.user.UserController
import com.user.modules.user.UserControllerImp
import org.koin.dsl.module


object ControllersInjection {
    val koinBeans =  module {
        single<PokemonController> { PokemonControllerImp() }
        single<UserController> { UserControllerImp() }
    }

}