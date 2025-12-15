package com.ojt.Telemondo_Act1.mapper

import com.luigivismara.shortuuid.ShortUuid
import com.ojt.Telemondo_Act1.dto.PostNoteDTO
import com.ojt.Telemondo_Act1.dto.PutNoteDTO
import com.ojt.Telemondo_Act1.model.Note
import org.mapstruct.*
import java.util.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface NoteMapper {
    @Named("stringToUUID")
    fun stringToUUID (id: String): UUID {
        return ShortUuid.decode(id)
    }

    @Named("uuidToString")
    fun uuidToString (id: UUID): String {
        return ShortUuid.encode(id).toString()
    }

    @Mapping(source = "username", target = "user")
    @Mapping(source = "content", target = "data")
    fun noteToPostNoteDTO(note: Note): PostNoteDTO

    @Mapping(source = "user", target = "username")
    @Mapping(source = "data", target = "content")
    fun postNoteDTOToNote(postNoteDTO: PostNoteDTO): Note

    @Mapping(source = "username", target = "user")
    @Mapping(source = "content", target = "data")
    @Mapping(source = "id", target = "id", qualifiedByName = ["uuidToString"])
    fun noteToPutNoteDTO(note: Note): PutNoteDTO

    @Mapping(source = "user", target = "username")
    @Mapping(source = "data", target = "content")
    @Mapping(source = "id", target = "id", qualifiedByName = ["stringToUUID"])
    fun putNoteDTOToNote(postNoteDTO: PutNoteDTO, @MappingTarget note: Note): Note
}
