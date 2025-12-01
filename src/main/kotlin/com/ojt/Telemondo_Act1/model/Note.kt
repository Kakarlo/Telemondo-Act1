package com.ojt.Telemondo_Act1.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "notes")
class Note (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String = "",
    var content: String = "",
    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    var createdAt: Instant? = null,
    @UpdateTimestamp
    @Column(name = "updatedAt")
    var updatedAt: Instant? = null,
)