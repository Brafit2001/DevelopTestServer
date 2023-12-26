package com.pokemon

import com.pokemon.api.injection.ApiInjection
import com.pokemon.database.DatabaseProvider
import com.pokemon.database.injection.DaoInjection
import com.pokemon.modules.injection.ControllersInjection
import com.pokemon.plugins.configureRouting
import com.pokemon.plugins.configureSerialization
import com.pokemon.statuspages.pokemonStatusPages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.module() {


    DatabaseProvider.init()

    install(Koin) {
        slf4jLogger()
        modules(
            ApiInjection.koinBeans,
            ControllersInjection.koinBeans,
            DaoInjection.koinBeans


        )
    }

    install(StatusPages) {
        pokemonStatusPages()
        exception<UnknownError> { call, _ ->
            call.respondText(
                "Internal server error",
                ContentType.Text.Plain,
                status = HttpStatusCode.InternalServerError
            )

        }
        exception<IllegalArgumentException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    configureSerialization()
    configureRouting()
}
