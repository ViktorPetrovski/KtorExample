package doc.ktor.users.data

import doc.ktor.database.table.Users
import doc.ktor.users.model.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalStateException

class UsersRepositoryImpl : UsersRepository {
    override suspend fun createUser(username: String, password: String): User {
        var insertedRow: InsertStatement<Number>? = null
        transaction {
            insertedRow = Users.insert { user ->
                user[Users.username] = username
                user[Users.passwordHash] = password
            }
        }
        val insertedPostRow = insertedRow?.resultedValues?.get(0)
                ?: throw IllegalStateException("Unable to create post.")
        return User(insertedPostRow)
    }

    override suspend fun findUser(userId: Int) = transaction {
        Users.select { Users.userId.eq(userId) }.map { User(it) }.singleOrNull()
    }
}