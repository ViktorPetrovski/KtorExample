package doc.ktor.users.data

import doc.ktor.users.model.User

interface UsersRepository {
    suspend fun createUser(username: String, password: String): User
    suspend fun findUser(userId: Int): User?
}