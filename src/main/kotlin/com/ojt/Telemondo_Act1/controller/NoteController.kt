package com.ojt.Telemondo_Act1.controller

import com.ojt.Telemondo_Act1.model.Note
import com.ojt.Telemondo_Act1.service.NoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class NoteController(@Autowired private val noteService: NoteService) {
    @GetMapping("/api/notes")
    fun getNotes(): List<Note> {
        return noteService.getAllNotes()
    }

    @GetMapping("/api/notes/summary")
    fun index(@RequestParam limit: Int = 5): List<Any> {
        // Show the last 5 notes by default
        return noteService.getNoteSummary(limit)
    }

    data class PostNoteDTO(val user: String, val data: String) {}

    @PostMapping("/api/notes")
    fun setNote(@RequestBody body: PostNoteDTO): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.createNote(body))
    }

    data class PutNoteDTO(val id: Long, val user: String, val data: String)

    @PutMapping("/api/notes")
    fun updateNote(@RequestBody body: PutNoteDTO): ResponseEntity<Any> {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(noteService.updateNote(body))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Input is not in valid format")
        }
    }

    @DeleteMapping("/api/notes/{id}")
    fun deleteNote(@PathVariable id: Long): ResponseEntity<String> {
        try {
            noteService.deleteNote(id)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Note not Found")
        }
        return ResponseEntity.status(HttpStatus.OK).body("Note Deleted")
    }
}
