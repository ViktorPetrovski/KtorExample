package doc.ktor

import database.DatabaseFactory
import doc.ktor.authentication.JwtService
import doc.ktor.authentication.hash
import doc.ktor.users.data.UsersRepositoryImpl
import doc.ktor.users.users
import post.route.post
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import post.data.PostsRepositoryImpl

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    DatabaseFactory.init()
    val usersRepository = UsersRepositoryImpl()
    val jwtService = JwtService()
    val hashFunction = { s: String -> hash(s) }

    configureFeatures()
    routing {
        post(PostsRepositoryImpl())
        users(jwtService, usersRepository, hashFunction)
    }
}

@KtorExperimentalAPI
fun Application.configureFeatures() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }
    install(io.ktor.features.DefaultHeaders)

    val usersRepository = UsersRepositoryImpl()
    val jwtService = JwtService()
    install(Authentication) {

        jwt {
            verifier(jwtService.verifier)
            realm = "Viktor Personal Server"
            validate { // 3
                val payload = it.payload
                val claim = payload.getClaim("id")
                val claimString = claim.asInt()
                val user = usersRepository.findUser(claimString) // 4
                user
            }
        }
    }
}
