package doc.ktor.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import doc.ktor.users.data.model.User
import java.util.*

class JwtService {
    private val issuer = "todoServer"
    private val jwtSecret = "898748674728934843" // 1
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    // 2
    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    // 3
    fun generateToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", user.userId)
        .withExpiresAt(expiresAt())
        .sign(algorithm)

    private fun expiresAt() =
        Date(System.currentTimeMillis() + 3_600_000 * 24) // 24 hours
}