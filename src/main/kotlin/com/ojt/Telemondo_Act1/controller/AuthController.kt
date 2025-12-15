package com.ojt.Telemondo_Act1.controller

import com.ojt.Telemondo_Act1.dto.SignInDTO
import com.ojt.Telemondo_Act1.repo.UserRepository
import com.ojt.Telemondo_Act1.security.JwtUtil
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepo: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
) {

    @PostMapping("/login")
    fun login(@RequestBody body: SignInDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user =
            userRepo.findByEmail(body.email)
                ?: return ResponseEntity.status(401).body("Invalid credentials")

        if (!passwordEncoder.matches(body.password, user.password)) {
            return ResponseEntity.status(401).body("Invalid credentials")
        }

        val token = jwtUtil.generateToken(user.email, user.roles.map { it.name })

        val cookie = Cookie("jwt", token)
        cookie.path = "/"
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseEntity.ok("Logged in")
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<String> {
        val cookie = Cookie("jwt", "")
        cookie.path = "/"
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity.ok("Logged out")
    }
}
