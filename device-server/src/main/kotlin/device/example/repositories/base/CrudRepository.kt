package device.example.repositories.base

import kotlinx.coroutines.flow.Flow

interface CrudRepository<T, ID> {

    suspend fun findAll(): List<T>

    suspend fun findById(id: ID): T?

    suspend fun save(entity: T): T?

    suspend fun delete(id: Int): Int?

    suspend fun update(entity: T): T?

    suspend fun deleteAll()
}