package post.route

import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import post.data.PostsRepository
import post.data.PostsRepositoryImpl

class PostRequest(val description: String)

fun Route.post(repository: PostsRepository) {
    post("/post/create") {
        val postRequest = call.receive<PostRequest>()
        call.respond(repository.createPost(postRequest.description))
    }

    get("/post/{id}") {
        val postId = call.parameters["id"]!!.toInt()
        call.respond(repository.getPost(postId)!!)
    }

    delete("/post/{id}") {
        val postId = call.parameters["id"]!!.toInt()
        call.respond(repository.deletePost(postId))
    }

    put("/post/{id}") {
        val postId = call.parameters["id"]!!.toInt()
        val postRequest = call.receive<PostRequest>()
        call.respond(repository.updatePost(postId, postRequest.description))
    }
}
