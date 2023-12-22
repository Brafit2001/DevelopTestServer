package com.pokemon.database

import com.pokemon.config.Config
import com.pokemon.database.dao.Pokemons
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseProvider{

    private val config = Config

    fun init() {
        val driverClassName = "org.mariadb.jdbc.Driver"
        val jdbcURL = "jdbc:mariadb://localhost:3306/${config.DATABASENAME}"
        val user = config.DATABASEUSER
        val password = config.DATABASEPASSWORD
        val database = Database.connect(jdbcURL, driverClassName, user, password)
        transaction(database) {
            SchemaUtils.create(Pokemons)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

interface DataBaseProviderContract{
    fun init()
    suspend fun <T> dbQuery(block: () -> T): T
}


