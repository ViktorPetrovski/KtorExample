package doc.ktor.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val userId: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val username = varchar("username", 50).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)
    override val primaryKey = PrimaryKey(Users.userId, name = "pk_users_id")
}