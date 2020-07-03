package doc.ktor

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import database.DatabaseFactory
import doc.ktor.authentication.JwtService
import doc.ktor.users.data.UsersRepositoryImpl
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
    configureFeatures()
    routing {
        post(PostsRepositoryImpl())
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

private val algorithm = Algorithm.HMAC256("secret")
private fun makeJwtVerifier(issuer: String, audience: String): JWTVerifier = JWT
    .require(algorithm)
    .withAudience(audience)
    .withIssuer(issuer)
    .build()