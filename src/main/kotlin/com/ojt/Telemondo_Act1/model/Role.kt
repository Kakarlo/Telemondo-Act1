package com.ojt.Telemondo_Act1.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "role")
class Role(
    @Id @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    val id: UUID? = null,

    @Column(name = "name",unique = true)
    var name: String
)
