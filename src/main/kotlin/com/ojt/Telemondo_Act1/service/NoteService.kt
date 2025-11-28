package com.ojt.Telemondo_Act1.service

import com.ojt.Telemondo_Act1.controller.NoteController
import com.ojt.Telemondo_Act1.model.Note
import com.ojt.Telemondo_Act1.repo.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NoteService (@Autowired private val noteRepo : NoteRepository) {

    fun getAllNotes() : List<Note> = noteRepo.findAll()

    fun getNoteSummary(limit: Int) : List<Any> = noteRepo.getNoteSummary(limit)

    fun getNoteById(id: Long) = noteRepo.findById(id)

    fun createNote(note: NoteController.PostBody): Note {
        if (note.username.isEmpty()){
            throw Exception("User is empty")
        }

        return noteRepo.save(Note().apply { this.username = note.username; this.content = note.content })
    }

    fun updateNote( id : Long, note : Note) {
        // verify later
        return noteRepo.findById(id).ifPresent { oldNote ->
            oldNote.content = note.content
            oldNote.updatedAt = LocalDateTime.now()
            noteRepo.save(oldNote)
        }
    }

    fun deleteNote(id: Long) {
        return noteRepo.deleteById(id)
    }

}