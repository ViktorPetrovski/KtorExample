package post

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import model.User
import post.data.PostsRepositoryImpl

fun Route.post() {
    post("/post/create") {
        val randomDescription = "Random new post"
        val repository = PostsRepositoryImpl()
        call.respond(repository.createPost(randomDescription))
    }

    get("/post/{id}") {
        val postId = call.parameters["id"]!!.toInt()
        val repository = PostsRepositoryImpl()
        call.respond(repository.getPost(postId)!!)
    }
}
