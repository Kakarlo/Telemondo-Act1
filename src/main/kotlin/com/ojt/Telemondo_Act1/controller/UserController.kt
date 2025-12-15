package com.ojt.Telemondo_Act1.controller

import com.luigivismara.shortuuid.ShortUuid
import com.ojt.Telemondo_Act1.model.User
import com.ojt.Telemondo_Act1.repo.RoleRepository
import com.ojt.Telemondo_Act1.repo.UserRepository
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepo: UserRepository,
    private val roleRepo: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @GetMapping
    fun getAll(): List<User?> {
        val users = userRepo.findAll()
        users.forEach { user -> println("${user.email}: ${ShortUuid.encode(user.id).toString()}") }
        return users
    }

    data class UserDto(
        val email: String,
        val username: String,
        val password: String,
        val roleIds: List<UUID>,
    )

    @PostMapping
    fun create(@RequestBody body: UserDto): ResponseEntity<User> {
        val roles = roleRepo.findAllById(body.roleIds).toMutableSet()
        val user =
            User(
                email = body.email,
                username = body.username,
                password = passwordEncoder.encode(body.password)!!,
                roles = roles,
            )
        return ResponseEntity.ok(userRepo.save(user))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody body: UserDto): ResponseEntity<User> {
        val user = userRepo.findById(ShortUuid.decode(id)).orElseThrow()
        user.roles.clear()
        user.roles.addAll(roleRepo.findAllById(body.roleIds))
        user.password = passwordEncoder.encode(body.password)!!
        return ResponseEntity.ok(userRepo.save(user))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<String> {
        userRepo.deleteById(ShortUuid.decode(id))
        return ResponseEntity.ok("Deleted")
    }
}
