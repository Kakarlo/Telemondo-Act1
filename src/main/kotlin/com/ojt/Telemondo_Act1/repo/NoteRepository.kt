package com.ojt.Telemondo_Act1.repo

import com.ojt.Telemondo_Act1.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Long> {
    @Query(
        value = "SELECT username, content FROM notes ORDER BY 'updatedAt' DESC LIMIT ?1",
        nativeQuery = true,
    )
    fun getNoteSummary(limit: Int): List<Any>

    fun getNoteByUsername(username: String): Note
}
