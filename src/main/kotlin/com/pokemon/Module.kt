package com.pokemon

import com.pokemon.database.DataBaseProviderContract
import com.pokemon.database.DatabaseProvider
import com.pokemon.plugins.configureRouting
import com.pokemon.plugins.configureSerialization
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.module() {


    DatabaseProvider.init()

    install(Koin) {
        slf4jLogger()
        modules()
    }

    configureSerialization()
    configureRouting()
}
