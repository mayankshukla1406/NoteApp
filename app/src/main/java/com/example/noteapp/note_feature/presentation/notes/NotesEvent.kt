package com.example.noteapp.note_feature.presentation.notes.components

import com.example.noteapp.note_feature.domain.model.Note
import com.example.noteapp.note_feature.domain.utility.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note : Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}