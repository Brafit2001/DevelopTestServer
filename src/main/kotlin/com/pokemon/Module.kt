package com.pokemon

import com.pokemon.auth.JwtConfig
import com.pokemon.database.DatabaseProvider
import com.pokemon.plugins.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

val jwtConfig = JwtConfig("secret")
fun Application.module() {


    install(Authentication) {
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }
    //Database Initiator
    DatabaseProvider.init()


    //Plugins configurator
    configureKoin()
    configureStatusPages()
    configureSerialization()
    configureRouting(jwtConfig)

}
