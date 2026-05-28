package com.avis.skycast.data.repository

import com.avis.skycast.data.local.User
import com.avis.skycast.data.local.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor(

    private val userDao: UserDao
) {

    suspend fun registerUser(
        user: User
    ) {

        userDao.insertUser(user)
    }

    suspend fun loginUser(
        email: String,
        password: String
    ): User? {

        return userDao.login(
            email,
            password
        )
    }
}