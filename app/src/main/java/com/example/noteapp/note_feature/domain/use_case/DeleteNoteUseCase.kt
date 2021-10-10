package com.example.noteapp.note_feature.domain.use_case

import com.example.noteapp.note_feature.domain.model.Note
import com.example.noteapp.note_feature.domain.repository.NoteRepository

class DeleteNoteUseCase
    (private val repository: NoteRepository) {

        suspend operator fun invoke(note:Note){
            repository.deleteNote(note)
        }
}