package com.pokemon.api.user

import com.pokemon.database.dao.UserDao
import com.pokemon.model.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object UserApiImpl : UserApi, KoinComponent {

    private val userDao by inject<UserDao>()

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    override suspend fun getUserById(id: Int): User?{
        return userDao.getUserById(id)
    }

    override suspend fun getUserByName(name: String): User?{
        return userDao.getUserByName(name)
    }

    override suspend fun createUser(postUser: PostUserBody): User? {
        return userDao.postUser(postUser)
    }

    override suspend fun editUser(id: Int, putUser: PutUserBody): User? {
        return userDao.putUser(id, putUser)
    }

    override suspend fun deleteUser(id: Int): Boolean {
        return userDao.deleteUser(id)
    }
}
