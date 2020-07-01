package doc.ktor

import database.DatabaseFactory
import features
import post.route.post
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.routing.routing
import io.netty.handler.codec.DefaultHeaders
import post.data.PostsRepositoryImpl

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    DatabaseFactory.init()
    routing {
        post(PostsRepositoryImpl())
        features()
    }
    install(io.ktor.features.DefaultHeaders)
}
