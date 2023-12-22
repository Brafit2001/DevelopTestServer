package com.pokemon

import com.pokemon.database.DatabaseProvider
import com.pokemon.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

