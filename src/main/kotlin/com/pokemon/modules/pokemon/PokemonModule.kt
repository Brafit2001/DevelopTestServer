package com.pokemon.modules.pokemon


import com.pokemon.model.PokeApiResponse
import com.pokemon.model.PostPokemonBody
import com.pokemon.model.PutPokemonBody
import com.pokemon.statuspages.PokemonNotFound
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache5.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.pokemonRouting(){



    route("/v1/api/pokemons"){

        val controller by inject<PokemonController>()

        get {
            val pokemonList = controller.findAll()
            if (pokemonList.isEmpty()){
                val pokeApiList = extractPokemons()
                pokeApiList.forEach {
                    controller.createPokemon(it)
                }
            }
            call.respond(pokemonList)
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

private suspend fun extractPokemons(): List<PostPokemonBody>{
    val client = HttpClient(Apache5) {
        engine {
            // this: Apache5EngineConfig
            followRedirects = true
            socketTimeout = 10_000
            connectTimeout = 10_000
            connectionRequestTimeout = 20_000
            customizeRequest {
                // this: RequestConfig.Builder
            }

        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.NONE //You can only request/response headers, include bodies or none
            filter { request ->
                request.url.host.contains("ktor.io")
            }
            sanitizeHeader {
                    header -> header == HttpHeaders.Authorization
            }
        }
        install(ContentNegotiation){
            json()
        }
    }
    val pokeApiResponse: String = client.get("https://pokeapi.co/api/v2/pokemon?limit=1018&offset=0").body()
    val convertResponse = Json.decodeFromString<PokeApiResponse>(pokeApiResponse)
    val pokeApiList: List<PostPokemonBody> = convertResponse.results
    return  pokeApiList
}