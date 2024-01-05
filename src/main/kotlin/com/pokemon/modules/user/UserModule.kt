package com.pokemon.modules.user

import com.pokemon.auth.JwtConfig
import com.pokemon.model.AuthResponse
import com.pokemon.model.LoginBody
import com.pokemon.model.PostUserBody
import com.pokemon.model.PutUserBody
import com.user.modules.user.UserController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.userRouting(jwtConfig: JwtConfig){
    val controller by inject<UserController>()

    post("/login"){

        val loginBody = call.receive<LoginBody>()
        val user = controller.searchUserByName(loginBody.name)
        val accessToken = jwtConfig.createAccessToken(JwtConfig.JwtUser(user.id, user.name))
        val refreshToken = jwtConfig.createRefreshToken(JwtConfig.JwtUser(user.id, user.name))
        val authResponse = AuthResponse(accessToken, refreshToken)
        call.respond(authResponse)
    }

    authenticate {
        get {
            val userList = controller.findAll()
            if (userList.isEmpty()){
                call.respondText("Database is Empty", status = HttpStatusCode.NoContent)
            }else{
                call.respond(userList)
            }

        }
    }

    post{
        val postUser = call.receive<PostUserBody>()
        controller.createUser(postUser)
        call.respondText("User stored correctly", status = HttpStatusCode.Created)
    }

    get("name/{name?}"){
        val name = call.parameters["name"]
        if (name != null) {
            try {
                val user = controller.searchUserByName(name)
                call.respond(user)
            }catch (e: UnknownError){
                throw UnknownError()
            }
        }else{
            call.respondText("Invalid Id", status = HttpStatusCode.BadRequest)
        }
    }
    get("id/{id?}"){
        val id = call.parameters["id"]?.toInt()
        if (id != null) {
            try {
                val user = controller.searchUserById(id)
                call.respond(user)
            }catch (e: UnknownError){
                call.respondText("Error")
            }
        }else{
            call.respondText("Invalid Id", status = HttpStatusCode.BadRequest)
        }
    }
    delete("{id}") {
        val id = call.parameters["id"]?.toInt()
        if (id != null) {
            controller.deleteUser(id)
            call.respondText("User deleted correctly", status = HttpStatusCode.OK)
        }
    }
    put ( "{id}"){
        val id = call.parameters["id"]?.toInt()
        val userReceived = call.receive<PutUserBody>()
        if (id != null){
            controller.updateUser(id, userReceived)
            call.respondText("User updated correctly", status = HttpStatusCode.OK)
        }else{
            call.respondText("Invalid Id", status = HttpStatusCode.BadRequest)
        }

    }


}