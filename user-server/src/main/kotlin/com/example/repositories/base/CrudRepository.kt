package com.example.repositories.base

interface CrudRepository<T, ID> {

    suspend fun findAll(): List<T>

    suspend fun findById(id: ID): T?

    suspend fun save(entity: T): T?

    suspend fun delete(entity: String): Boolean

    suspend fun update(entity: T): Boolean

    suspend fun deleteAll()

}