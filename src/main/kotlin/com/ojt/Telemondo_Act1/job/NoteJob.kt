package com.ojt.Telemondo_Act1.job

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ojt.Telemondo_Act1.dto.PostNoteDTO
import com.ojt.Telemondo_Act1.dto.PutNoteDTO
import com.ojt.Telemondo_Act1.service.NoteService
import java.time.LocalDateTime
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GetNoteJob(@Autowired val noteService: NoteService) : Job {
    override fun execute(context: JobExecutionContext) {
        val count = noteService.getNoteCount()
        println("There are a total of $count notes")
        println("getNoteCountDelayed() time:${LocalDateTime.now()}")
    }
}

@Component
class CreateNoteJob(@Autowired val noteService: NoteService) : Job {
    override fun execute(context: JobExecutionContext) {
        val payloadJson = context.mergedJobDataMap["body"] as String
        val objectMapper = jacksonObjectMapper()
        val body: PostNoteDTO = objectMapper.readValue(payloadJson)
        noteService.createNote(body)
        println("Creating entity with name=${body.user}, data=${body.data}")
        println("createNoteDelayed() ended:${LocalDateTime.now()}")
    }
}

@Component
class UpdateNoteJob(@Autowired val noteService: NoteService) : Job {
    override fun execute(context: JobExecutionContext) {
        val payloadJson = context.mergedJobDataMap["body"] as String
        val objectMapper = jacksonObjectMapper()
        val body: PutNoteDTO = objectMapper.readValue(payloadJson)
        noteService.updateNote(body)
        println("Updating entity id=${body.id}, data=${body.data}")
        println("updateNoteDelayed() ended:${LocalDateTime.now()}")
    }
}

@Component
class DeleteNoteJob(@Autowired val noteService: NoteService) : Job {
    override fun execute(context: JobExecutionContext) {
        val id = context.mergedJobDataMap["id"] as String
        noteService.deleteNote(id)
        println("Deleting entity id=${id}")
        println("deleteNoteDelayed() ended:${LocalDateTime.now()}")
    }
}
