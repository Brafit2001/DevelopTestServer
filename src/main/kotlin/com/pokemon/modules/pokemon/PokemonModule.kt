package com.pokemon.modules.pokemon


import com.pokemon.model.PostPokemonBody
import com.pokemon.model.PutPokemonBody
import com.pokemon.statuspages.PokemonNotFound
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.pokemonRouting(){
    route("/v1/api/pokemons"){

        val controller by inject<PokemonController>()

        get {
            call.respond(controller.findAll())
        }

        get("{id?}"){
            val id = call.parameters["id"]?.toInt()
            if (id != null) {
                try {
                    val pokemon = controller.searchPokemon(id)
                    call.respond(pokemon)
                }catch (e: PokemonNotFound){
                    call.respondText(e.message)
                }



            }
        }
        post{
            val postPokemon = call.receive<PostPokemonBody>()
            controller.createPokemon(postPokemon)
            call.respondText("Pokemon stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id}") {
            val id = call.parameters["id"]?.toInt()
            if (id != null) {
                controller.deletePokemon(id)
                call.respondText("Pokemon deleted correctly", status = HttpStatusCode.OK)
            }
        }
        put ( "{id}"){
            val id = call.parameters["id"]?.toInt()
            val pokemonReceived = call.receive<PutPokemonBody>()
            if (id != null){
                controller.updatePokemon(id, pokemonReceived)
                call.respondText("Pokemon updated correctly", status = HttpStatusCode.OK)
            }else{
                call.respondText("Invalid Id", status = HttpStatusCode.BadRequest)
            }

        }

        /*
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

         */
    }
}