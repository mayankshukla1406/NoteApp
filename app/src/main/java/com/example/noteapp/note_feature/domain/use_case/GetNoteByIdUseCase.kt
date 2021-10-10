package com.example.noteapp.note_feature.domain.use_case

import com.example.noteapp.note_feature.domain.model.Note
import com.example.noteapp.note_feature.domain.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id:Int): Note?{
        return repository.getNoteById(id)
    }
}