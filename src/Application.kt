package doc.ktor

import database.DatabaseFactory
import features
import post.route.post
import io.ktor.application.Application
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    DatabaseFactory.init()
    routing {
        post()
        features()
    }
}
