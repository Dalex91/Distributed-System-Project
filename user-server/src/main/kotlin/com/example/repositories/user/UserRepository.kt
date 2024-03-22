package com.example.repositories.user

import com.example.models.User
import com.example.repositories.base.CrudRepository

interface UserRepository : CrudRepository<User, Int> {

    suspend fun findByUsername(username: String): User?

    suspend fun checkUserNameAndPassword(username: String, password: String): User?

}