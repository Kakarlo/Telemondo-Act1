package com.ojt.Telemondo_Act1.controller

import com.luigivismara.shortuuid.ShortUuid
import com.ojt.Telemondo_Act1.model.Role
import com.ojt.Telemondo_Act1.repo.RoleRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/roles")
class RoleController(private val roleRepo: RoleRepository) {

    @GetMapping
    fun getAll(): List<Role?> {
        val roles = roleRepo.findAll()
        roles.forEach { role -> println("${role.name}: ${ShortUuid.encode(role.id).toString()}") }
        return roles
    }

    @PostMapping fun create(@RequestBody body: Role): Role = roleRepo.save(body)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody body: Role): Role {
        val existing = roleRepo.findById(ShortUuid.decode(id)).orElseThrow()
        val updated = existing.apply { name = body.name }
        return roleRepo.save(updated)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<String> {
        roleRepo.deleteById(ShortUuid.decode(id))
        return ResponseEntity.ok("Deleted")
    }
}
