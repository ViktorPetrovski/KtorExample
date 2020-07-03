package doc.ktor.database.table

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 50).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)
    override val primaryKey = PrimaryKey(id, name = "pk_users_id")
}