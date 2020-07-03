package doc.ktor.users.model

import doc.ktor.database.table.Users
import io.ktor.auth.Principal
import org.jetbrains.exposed.sql.ResultRow
import java.io.Serializable

data class User(
        val userId: Int,
        val username: String
): Serializable, Principal {
    constructor(row: ResultRow) : this(
            userId = row[Users.id],
            username = row[Users.username]
    )
}