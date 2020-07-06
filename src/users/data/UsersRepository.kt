package doc.ktor.users.data

import doc.ktor.users.data.model.User

interface UsersRepository {
    suspend fun createUser(username: String, password: String): User
    suspend fun findUser(userId: Int): User?
    suspend fun findUserByUsername(username: String): User?
}