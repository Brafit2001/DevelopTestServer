package com.pokemon.plugins

import com.pokemon.statuspages.pokemonStatusPages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages(){
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
}