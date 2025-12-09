package com.ojt.Telemondo_Act1.mapper

import com.ojt.Telemondo_Act1.dto.PostNoteDTO
import com.ojt.Telemondo_Act1.dto.PutNoteDTO
import com.ojt.Telemondo_Act1.model.Note
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface NoteMapper {

    @Mapping(source = "username", target = "user")
    @Mapping(source = "content", target = "data")
    fun noteToPostNoteDTO(note: Note): PostNoteDTO

    @Mapping(source = "user", target = "username")
    @Mapping(source = "data", target = "content")
    fun postNoteDTOToNote(postNoteDTO: PostNoteDTO): Note

    @Mapping(source = "username", target = "user")
    @Mapping(source = "content", target = "data")
    fun noteToPutNoteDTO(note: Note): PutNoteDTO

    @Mapping(source = "user", target = "username")
    @Mapping(source = "data", target = "content")
    fun putNoteDTOToNote(postNoteDTO: PutNoteDTO, @MappingTarget note: Note): Note
}
