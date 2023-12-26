package com.pokemon.plugins

import com.pokemon.api.injection.ApiInjection
import com.pokemon.database.injection.DaoInjection
import com.pokemon.modules.injection.ControllersInjection
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            ApiInjection.koinBeans,
            ControllersInjection.koinBeans,
            DaoInjection.koinBeans
        )
    }
}
