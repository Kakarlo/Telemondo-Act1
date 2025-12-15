package com.ojt.Telemondo_Act1.repo

import com.ojt.Telemondo_Act1.model.Role
import com.ojt.Telemondo_Act1.model.User
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository //
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
}

@Repository //
interface RoleRepository : JpaRepository<Role, UUID> {
    fun findByName(name: String): Role?
}
