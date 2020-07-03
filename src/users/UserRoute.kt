package doc.ktor.users

import doc.ktor.authentication.JwtService
import doc.ktor.users.data.UsersRepository
import doc.ktor.users.model.User
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import org.apache.http.protocol.HTTP

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
    authenticate("jwt"){
        get("/users/me") {
            val user = call.authentication.principal<User>()
            call.respond(user.toString())
        }
    }
}