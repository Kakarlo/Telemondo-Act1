package com.ojt.Telemondo_Act1.dto

data class PostNoteDTO(val user: String, val data: String, val delay: Int? = 0) {}

data class PutNoteDTO(val id: String, val user: String, val data: String, val delay: Int? = 0) {}
