package com.pokemon.routes


import com.pokemon.api.pokemon.*
import com.pokemon.model.Pokemon
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.netty.handler.codec.http2.Http2PushPromiseFrame

fun Route.pokemonRouting(){
    route("/v1/api/pokemons"){
        get {
                if ( dao.getAllPokemons().isNotEmpty()){
                    call.respond(dao.getAllPokemons())
                }else{
                    call.respondText("No existe ningun pokemon", status = HttpStatusCode.NoContent)
                }

            }
        get("{id?}"){
                val id = call.parameters["id"]?.toInt()
                    ?: return@get call.respondText("Missing Id", status = HttpStatusCode.BadRequest)
                try{
                    val pokemon = dao.getPokemonById(id)
                        ?: return@get call.respondText("No pokemon with user $id", status = HttpStatusCode.NotFound
                    )
                    call.respond(pokemon)
                }catch (_: NumberFormatException){
                    call.respondText("Only id numbers accepted", status = HttpStatusCode.NotAcceptable)
                }
            }
        post{
                val pokemon = call.receive<Pokemon>()
                dao.createPokemon(pokemon.name, pokemon.url)
                call.respondText("Pokemon stored correctly", status = HttpStatusCode.Created)
            }
        delete("{id?}"){
                val id = call.parameters["id"]?.toInt()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest)
                dao.deletePokemon(id)
                call.respondRedirect("/pokemon")
            }
        put("{id}") {

                    val pokemonReceived = call.receive<Pokemon>()
                    val pokemonSearch = dao.getPokemonById(pokemonReceived.id)
                    if (pokemonSearch == null){
                        dao.createPokemon(pokemonReceived.name, pokemonReceived.url)
                        call.respondText(
                            "Pokemon was not in DB, created successfully",
                            status = HttpStatusCode.Created
                        )
                    }else{
                        dao.editPokemon(pokemonSearch.id, pokemonSearch.name, pokemonSearch.url)
                        call.respondText("Pokemon updated correctly", status = HttpStatusCode.OK)
                    }

                }
    }
}