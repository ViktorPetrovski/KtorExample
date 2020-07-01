package post.route

import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import post.data.PostsRepositoryImpl

class PostRequest(val description: String)

fun Route.post() {
    post("/post/create") {
        val postRequest = call.receive<PostRequest>()
        val repository = PostsRepositoryImpl()
        call.respond(repository.createPost(postRequest.description))
    }

    get("/post/{id}") {
        val postId = call.parameters["id"]!!.toInt()
        val repository = PostsRepositoryImpl()
        call.respond(repository.getPost(postId)!!)
    }

    delete("/post/{id}") {
        val postId = call.parameters["id"]!!.toInt()
        val repository = PostsRepositoryImpl()
        call.respond(repository.deletePost(postId))
    }
}
