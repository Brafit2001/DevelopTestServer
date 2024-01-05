package com.pokemon.plugins

import com.pokemon.auth.JwtConfig
import com.pokemon.modules.pokemon.pokemonRouting
import com.pokemon.modules.user.userRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(jwtConfig: JwtConfig) {
    routing {
        get("/") {
            call.respondText("Welcome to Pokemon Data API!!")
        }
        route("/v1/api/"){
             route("/pokemons"){
                 pokemonRouting()
             }

            route("/users"){
                userRouting(jwtConfig)
            }
        }


    }
}
