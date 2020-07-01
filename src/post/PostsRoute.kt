package post

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import model.User

fun Route.home() {
    get("/user/{name}") {
        call.respond(User(call.parameters["name"].toString(), "Randomm text"))
    }
}
