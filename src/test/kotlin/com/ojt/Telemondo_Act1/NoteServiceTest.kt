package com.ojt.Telemondo_Act1

import com.github.database.rider.core.api.dataset.DataSet
import com.ojt.Telemondo_Act1.dto.PostNoteDTO
import com.ojt.Telemondo_Act1.dto.PutNoteDTO
import com.ojt.Telemondo_Act1.service.NoteService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DataSet(value = ["db/datasets/notes.json"])
class NoteServiceTest @Autowired constructor(private val noteService: NoteService) : BaseTest() {
    @Test
    fun testTableData() {
        assertEquals(4, noteService.getNoteSummary(10).count())
    }

    @Test
    fun testCreateNote() {
        val data = PostNoteDTO(user = "Test", data = "First Note")
        val note = noteService.createNote(data)

        assertEquals("Test", note.username)
        assertEquals("First Note", note.content)
        assertEquals(6, note.id)
    }

    @Test
    fun testUpdateNote() {
        val data = PutNoteDTO(id = 5, user = "Test", data = "Edited Note")
        val note = noteService.updateNote(data)

        assertEquals("Test", note.username)
        assertEquals("Edited Note", note.content)
        assertEquals(5, note.id)
    }

    @Test
    fun testDeleteNote() {
        noteService.deleteNote(1)
        assertEquals(3, noteService.getNoteSummary(10).count())
    }
}
