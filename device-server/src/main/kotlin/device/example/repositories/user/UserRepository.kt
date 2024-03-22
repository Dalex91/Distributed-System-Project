package device.example.repositories.user

interface UserRepository {

    suspend fun save(userId: Int): Int?

    suspend fun delete(id: Int): Int?

    suspend fun findById(id: Int): Int?
}