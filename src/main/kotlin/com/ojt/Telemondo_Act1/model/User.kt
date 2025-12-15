package com.ojt.Telemondo_Act1.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
class User (
    @Id @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    val id: UUID? = null,
    var username: String,
    @JsonIgnore
    var password: String,
    @Column(unique = true)
    var email: String,

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    var createdAt: Instant? = null,
    @UpdateTimestamp @Column(name = "updatedAt")
    var updatedAt: Instant? = null,

    @ManyToMany(fetch = FetchType.EAGER) // synchronous?
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf()
//    var roles: List<Role> = emptyList()

)
