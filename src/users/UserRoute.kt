package doc.ktor.users

import doc.ktor.authentication.JwtService
import doc.ktor.users.data.UsersRepository
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

data class NewUserRequest(val username: String, val password: String)

fun Route.users(
    jwtService: JwtService,
    db: UsersRepository,
    hashFunction: (String) -> String
) {
    post("/users/create") {
        val userRequest = call.receive<NewUserRequest>()
        val hashedPassword = hashFunction(userRequest.password)
        val newUser = db.createUser(userRequest.username, hashedPassword)
        call.respond(jwtService.generateToken(newUser))
    }
}