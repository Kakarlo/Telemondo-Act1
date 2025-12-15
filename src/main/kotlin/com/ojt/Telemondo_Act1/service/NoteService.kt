package com.ojt.Telemondo_Act1.service

import com.luigivismara.shortuuid.ShortUuid
import com.ojt.Telemondo_Act1.dto.PostNoteDTO
import com.ojt.Telemondo_Act1.dto.PutNoteDTO
import com.ojt.Telemondo_Act1.mapper.NoteMapper
import com.ojt.Telemondo_Act1.model.Note
import com.ojt.Telemondo_Act1.repo.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class NoteService(
    @Autowired private val noteRepo: NoteRepository,
    @Autowired private val noteMapper: NoteMapper,
) {

    fun getAllNotes(): List<Note> {
        val notes = noteRepo.findAll()
        return notes.map({note -> note.apply {
            content = "${username}: ${content}"
            username = ShortUuid.encode(id).toString()
        }})
    }

    fun getNoteSummary(limit: Int): List<Any> = noteRepo.getNoteSummary(limit)


    fun getNoteCount() = noteRepo.count()

    fun createNote(body: PostNoteDTO): Note {
        if (body.user.isEmpty()) {
            throw Exception("User is empty")
        }
        val note = noteMapper.postNoteDTOToNote(body)
        return noteRepo.save(note)
    }

    fun updateNote(body: PutNoteDTO): Note {
        // verify later
        val verify =
            noteRepo.findByIdOrNull(ShortUuid.decode(body.id))
                ?: throw Exception("Note does not exist")
        val note = noteMapper.putNoteDTOToNote(body, verify)

        return noteRepo.save(note)
    }

    fun deleteNote(id: String) {
        return noteRepo.deleteById(ShortUuid.decode(id))
    }
}
