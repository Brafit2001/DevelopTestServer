package com.user.modules.user

import com.pokemon.api.user.UserApi
import com.pokemon.database.DatabaseProvider.dbQuery
import com.pokemon.model.PostUserBody
import com.pokemon.model.PutUserBody
import com.pokemon.model.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserControllerImp: UserController, KoinComponent {

    private val userApi by inject<UserApi>()

    override suspend fun findAll(): List<User> = dbQuery {
        userApi.getAllUsers()
    }

    override suspend fun searchUserById(userId: Int): User = dbQuery {
        userApi.getUserById(userId) ?: throw UnknownError("Internal Error")
    }

    override suspend fun searchUserByName(userName: String): User = dbQuery {
        userApi.getUserByName(userName) ?: throw UnknownError("Internal Error")
    }

    override suspend fun createUser(postUser: PostUserBody): User = dbQuery {
        userApi.createUser(postUser) ?: throw UnknownError("Internal Error")
    }

    override suspend fun deleteUser(userId: Int) {
        dbQuery {
            userApi.deleteUser(userId)
        }
    }

    override suspend fun updateUser(userId: Int, putUser: PutUserBody): User = dbQuery{
        try {
            searchUserById(userId)
            userApi.editUser(userId, putUser) ?: throw UnknownError("Internal Error")
        }catch (e: UnknownError){
            TODO()
        }

    }


}

interface UserController {
    suspend fun searchUserByName(userName: String): User
    suspend fun searchUserById(userId: Int): User
    suspend fun findAll(): List<User>
    suspend fun createUser(postUser: PostUserBody): User
    suspend fun deleteUser(userId: Int)
    suspend fun updateUser(userId: Int, putUser: PutUserBody): User


}