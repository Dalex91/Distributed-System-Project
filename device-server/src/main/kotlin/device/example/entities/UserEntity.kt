package device.example.entities

import org.jetbrains.exposed.sql.Table

object UserEntity : Table("users") {
    val userID = integer("userId").uniqueIndex()
    override val primaryKey = PrimaryKey(userID)
}