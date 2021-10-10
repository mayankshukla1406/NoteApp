package com.example.noteapp.note_feature.domain.use_case

data class NoteUseCases(
    val getNotes    : GetNotesUseCase,
    val deleteNote  : DeleteNoteUseCase,
    val addNote     : AddNoteUseCase,
    val getNoteById : GetNoteByIdUseCase
)
