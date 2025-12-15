package com.ojt.Telemondo_Act1

import com.github.database.rider.core.api.dataset.DataSet
import com.luigivismara.shortuuid.ShortUuid
import com.ojt.Telemondo_Act1.dto.PostNoteDTO
import com.ojt.Telemondo_Act1.dto.PutNoteDTO
import com.ojt.Telemondo_Act1.service.NoteService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

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
        assertEquals(5, noteService.getNoteSummary(10).count())
    }

    @Test
    fun testUpdateNote() {
        val id = ShortUuid.encode(UUID.fromString("019b0745-efc1-7118-9cb6-01873e83d86a")).toString()
        val data = PutNoteDTO(id = id, user = "Test", data = "Edited Note")
        val note = noteService.updateNote(data)

        assertEquals("Test", note.username)
        assertEquals("Edited Note", note.content)
        assertEquals("019b0745-efc1-7118-9cb6-01873e83d86a", note.id.toString())
    }

    @Test
    fun testDeleteNote() {
        val id = ShortUuid.encode(UUID.fromString("019b0746-1467-72b1-9e30-72443f1daecc")).toString()
        noteService.deleteNote(id)
        assertEquals(3, noteService.getNoteSummary(10).count())
    }
}
