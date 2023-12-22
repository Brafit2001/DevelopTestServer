package com.pokemon.plugins

import com.pokemon.routes.pokemonRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello world!")
        }
        pokemonRouting()
    }
}
