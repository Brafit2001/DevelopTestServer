package com.pokemon.database.dao


import com.pokemon.model.PostUserBody
import com.pokemon.model.PutUserBody
import com.pokemon.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


object Users : Table("users"), UserDao{
    val id = integer("id").autoIncrement()
    var name = varchar("name", 79)
    var password = varchar("password", 128)
    var email = varchar("email", 128)

    override val primaryKey = PrimaryKey(id)

    override fun getAllUsers() : List<User> {
        return selectAll().map(::resultRowToUser)
    }

    override fun getUserById(userId: Int): User?{
        return select { id eq userId }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override fun getUserByName(userName: String): User?{
        return select {name eq userName }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override fun postUser(postUser: PostUserBody): User? {
        val insertStatement = insert {
            it[name] = postUser.name
            it[password] = postUser.password
            it[email] = postUser.email

        }
        return insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override fun deleteUser(userId: Int): Boolean {
        return deleteWhere { id eq userId } > 0
    }

    override fun putUser(userId: Int, putUser: PutUserBody): User? {
        update({ id eq userId }) {
            it[name] = putUser.name
            it[password] = putUser.password
            it[email] = putUser.email
        } > 0
        return getUserById(userId)
    }
}

private fun resultRowToUser(row: ResultRow) =
    User(
        id = row[Users.id],
        name = row[Users.name],
        password = row[Users.password],
        email = row[Users.email],
    )

interface UserDao{
    fun getUserById(userId: Int): User?
    fun getUserByName(userName: String): User?
    fun getAllUsers(): List<User>
    fun postUser(postUser: PostUserBody): User?
    fun deleteUser(userId: Int): Boolean
    fun putUser(userId: Int, putUser: PutUserBody): User?
}
