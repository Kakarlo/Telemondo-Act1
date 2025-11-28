package com.ojt.Telemondo_Act1.controller

import com.ojt.Telemondo_Act1.model.Note
import com.ojt.Telemondo_Act1.service.NoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
open class NoteController (@Autowired private val noteService : NoteService) {
    @GetMapping("/")
    fun index(@RequestParam limit: Int = 5): List<Any>{
        // Show the last 5 notes by default
        return noteService.getNoteSummary(limit)
    }

    @GetMapping("/notes")
    fun getNotes(@RequestParam note : String = ""): List<Note> {
        // Must return notes with all details
        return noteService.getAllNotes()
    }

    data class PostBody(val username: String, val content: String)

    @PostMapping("/notes")
    fun setNote(@RequestBody body : PostBody): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.createNote(body))
    }

    @PutMapping("/notes/{id}")
    fun updateNote(@PathVariable id : Long, @RequestBody body : Note): ResponseEntity<Unit> {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                .body(noteService.updateNote(id, body))
        }catch (e : Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Input is not in valid format")
        }
    }

    @DeleteMapping("/notes/{id}")
    fun deleteNote(@PathVariable id : Long): ResponseEntity<String> {
        try {
            noteService.deleteNote(id)
        }
        catch (e: Exception) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Note not Found"
            )
        }
        return ResponseEntity.status(HttpStatus.OK).body("Note Deleted")
    }

}