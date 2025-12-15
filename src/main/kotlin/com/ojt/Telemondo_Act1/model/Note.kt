package com.ojt.Telemondo_Act1.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.Instant
import java.util.*

@Entity
@Table(name = "notes")
class Note(
    @Id @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    var id: UUID? = null,
    @NotNull
    var username: String = "",
    var content: String? = "",
    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    var createdAt: Instant? = null,
    @UpdateTimestamp @Column(name = "updatedAt")
    var updatedAt: Instant? = null,
)
