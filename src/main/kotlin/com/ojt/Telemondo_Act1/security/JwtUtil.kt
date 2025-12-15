package com.ojt.Telemondo_Act1.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date
import org.springframework.stereotype.Component

@Component
class JwtUtil {

    private val secret = "c9cab96e9a67106277968cef085797d8"
    private val algorithm = Algorithm.HMAC256(secret)
    private val expirationMs = 1000 * 60 * 5 // 5 Mins

    fun generateToken(email: String, roles: List<String>): String {
        return JWT.create()
            .withSubject(email)
            .withClaim("roles", roles)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationMs))
            .sign(algorithm)
    }

    fun validateToken(token: String): String? {
        return try {
            val decoded = JWT.require(algorithm).build().verify(token)
            decoded.subject
        } catch (e: Exception) {
            null
        }
    }

    fun extractToken(token: String): DecodedJWT? {
        return try {
            JWT.require(algorithm).build().verify(token)
        } catch (e: Exception) {
            null
        }
    }
}
