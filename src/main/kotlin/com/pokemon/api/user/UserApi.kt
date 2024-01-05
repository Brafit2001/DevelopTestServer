package com.pokemon.api.user

import com.pokemon.model.*

interface UserApi {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByName(name: String): User?
    suspend fun createUser(postUser: PostUserBody): User?
    suspend fun editUser(id: Int, putUser: PutUserBody): User?
    suspend fun deleteUser(id: Int): Boolean
}