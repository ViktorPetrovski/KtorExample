package doc.ktor

import database.DatabaseFactory
import post.route.post
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.routing
import post.data.PostsRepositoryImpl

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    DatabaseFactory.init()
    configureFeatures()
    routing {
        post(PostsRepositoryImpl())
    }
}

fun Application.configureFeatures() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }
    install(io.ktor.features.DefaultHeaders)
}