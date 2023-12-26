package com.pokemon.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.pokemonStatusPages() {
    exception<InvalidPokemonException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.localizedMessage)
    }
    exception<PokemonNotFound> { call, cause ->
        call.respond(HttpStatusCode.NotFound, cause.localizedMessage)
    }

}

data class InvalidPokemonException(override val message: String = "Invalid Pokemon") : Exception()
data class PokemonNotFound(override val message: String = "Pokemon was not Found") : Exception()