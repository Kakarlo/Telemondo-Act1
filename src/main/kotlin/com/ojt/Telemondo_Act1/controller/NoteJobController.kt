package com.ojt.Telemondo_Act1.controller

import com.ojt.Telemondo_Act1.dto.PostNoteDTO
import com.ojt.Telemondo_Act1.dto.PutNoteDTO
import com.ojt.Telemondo_Act1.service.NoteJobService
import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/notes/job")
class NoteJobController(@Autowired private val noteJobService: NoteJobService) {
    @GetMapping("")
    fun getNotes(@RequestParam delay: Int): String {
        // Reoccurring job (inquire summary or count)
        println("getNoteCountDelayed() started:${LocalDateTime.now()}")
        noteJobService.getNoteCountDelayed(delay)
        return "Job is running"
    }

    @DeleteMapping("")
    fun deleteNote(): String {
        noteJobService.deleteGetNoteCount()
        return "Job is running"
    }

    @PostMapping("")
    fun setNote(@RequestBody body: PostNoteDTO): ResponseEntity<Any> {
        println("createNoteDelayed() started:${LocalDateTime.now()}")
        return ResponseEntity.status(HttpStatus.OK).body(noteJobService.createNoteDelayed(body))
    }

    @PutMapping("")
    fun updateNote(@RequestBody body: PutNoteDTO): ResponseEntity<Any> {
        try {
            println("updateNoteDelayed() started:${LocalDateTime.now()}")
            return ResponseEntity.status(HttpStatus.OK).body(noteJobService.updateNoteDelayed(body))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Input is not in valid format")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Long, @RequestParam delay: Int): ResponseEntity<String> {
        try {
            println("deleteNoteDelayed() started:${LocalDateTime.now()}")
            noteJobService.deleteNoteDelayed(id, delay)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Note not Found")
        }
        return ResponseEntity.status(HttpStatus.OK).body("Note Deleted with ${delay}s of delay")
    }
}
