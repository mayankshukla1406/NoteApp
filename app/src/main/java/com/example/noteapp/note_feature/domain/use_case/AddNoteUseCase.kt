package com.example.noteapp.note_feature.domain.use_case

import com.example.noteapp.note_feature.domain.model.InvalidAddNoteException
import com.example.noteapp.note_feature.domain.model.Note
import com.example.noteapp.note_feature.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidAddNoteException::class)
    suspend operator fun invoke(note:Note){
        if(note.title.isBlank())
        {
            throw InvalidAddNoteException("Please Provide the title for Note")
        }
        if(note.content.isBlank())
        {
            throw InvalidAddNoteException("Please Provide the content for Note")
        }
        repository.insertNote(note)
    }
}