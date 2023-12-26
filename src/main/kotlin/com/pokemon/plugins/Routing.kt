package com.pokemon.plugins

import com.pokemon.modules.pokemon.pokemonRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Welcome to Pokemon Data API!!")
        }
        pokemonRouting()
    }
}
