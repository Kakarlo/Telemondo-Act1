package com.ojt.Telemondo_Act1.service

import com.ojt.Telemondo_Act1.controller.NoteController
import com.ojt.Telemondo_Act1.mapper.NoteMapper
import com.ojt.Telemondo_Act1.model.Note
import com.ojt.Telemondo_Act1.repo.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class NoteService (
    @Autowired private val noteRepo : NoteRepository,
    @Autowired private val noteMapper : NoteMapper,
) {

    fun getAllNotes() : List<Note> = noteRepo.findAll()

    fun getNoteSummary(limit: Int) : List<Any> = noteRepo.getNoteSummary(limit)

    fun getNoteById(id: Long) = noteRepo.findById(id)

    fun createNote(body: NoteController.PostNoteDTO): Note {
        if (body.user.isEmpty()){
            throw Exception("User is empty")
        }
        val note = noteMapper.postNoteDTOToNote(body)
        return noteRepo.save(note)
    }

    fun updateNote(body: NoteController.PutNoteDTO): Any {
        // verify later
        val verify = noteRepo.findByIdOrNull(body.id) ?: throw Exception("Note does not exist")
        val note = noteMapper.putNoteDTOToNote(body, verify)

        return noteRepo.save(note)
    }

    fun deleteNote(id: Long) {
        return noteRepo.deleteById(id)
    }

}