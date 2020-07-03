package doc.ktor.users.model

import doc.ktor.database.table.Users
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

data class User(
        val userId: UUID,
        val username: String,
        val passwordHash: String
) {
    constructor(row: ResultRow) : this(
            userId = row[Users.userId],
            username = row[Users.username],
            passwordHash = row[Users.passwordHash]
    )
}