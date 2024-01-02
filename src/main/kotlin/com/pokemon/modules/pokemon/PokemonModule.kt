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
            val pokemonList = controller.findAll()
            if (pokemonList.isEmpty()){
                call.respondText("Database is Empty", status = HttpStatusCode.NoContent)
            }else{
                call.respond(pokemonList)
            }

        }
        get("name/{name?}"){
            val name = call.parameters["name"]
            if (name != null) {
                try {
                    val pokemon = controller.searchPokemonByName(name)
                    call.respond(pokemon)
                }catch (e: PokemonNotFound){
                    throw PokemonNotFound()
                }
            }else{
                call.respondText("Invalid Id", status = HttpStatusCode.BadRequest)
            }
        }

        get("id/{id?}"){
            val id = call.parameters["id"]?.toInt()
            if (id != null) {
                try {
                    val pokemon = controller.searchPokemonById(id)
                    call.respond(pokemon)
                }catch (e: PokemonNotFound){
                    call.respondText(e.message)
                }
            }else{
                call.respondText("Invalid Id", status = HttpStatusCode.BadRequest)
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

    }
}