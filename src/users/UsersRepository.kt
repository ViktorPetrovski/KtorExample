package doc.ktor.users

import doc.ktor.users.model.User

interface UsersRepository {
    suspend fun createUser(username: String, password: String): User
}