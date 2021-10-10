package com.example.noteapp.note_feature.presentation.notes.components

import com.example.noteapp.note_feature.domain.model.Note
import com.example.noteapp.note_feature.domain.utility.NoteOrder
import com.example.noteapp.note_feature.domain.utility.OrderType

data class NotesState(
    val notes : List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible : Boolean = false
)
