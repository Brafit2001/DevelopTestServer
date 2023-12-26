package com.pokemon

import com.pokemon.database.DatabaseProvider
import com.pokemon.plugins.configureKoin
import com.pokemon.plugins.configureRouting
import com.pokemon.plugins.configureSerialization
import com.pokemon.plugins.configureStatusPages
import io.ktor.server.application.*

fun Application.module() {
    //Database Initiator
    DatabaseProvider.init()

    //Plugins configurator
    configureKoin()
    configureStatusPages()
    configureSerialization()
    configureRouting()
}
