package com.example.noteapp.note_feature.domain.use_case

import android.util.Log
import com.example.noteapp.note_feature.domain.model.Note
import com.example.noteapp.note_feature.domain.repository.NoteRepository
import com.example.noteapp.note_feature.domain.utility.NoteOrder
import com.example.noteapp.note_feature.domain.utility.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetNotesUseCase(
    private val repository : NoteRepository
) {
    operator fun invoke(
        noteOrder : NoteOrder = NoteOrder.Date(OrderType.Descending)
    ) : Flow<List<Note>>{
        return repository.getAllNotes().map {notes->
            Log.d("allnotes",notes.toString())
            when(noteOrder.orderType){
                is OrderType.Ascending->{
                    when(noteOrder){
                        is NoteOrder.Title-> notes.sortedBy{ it.title.lowercase() }
                        is NoteOrder.Date->notes.sortedBy { it.timestamp }
                        is NoteOrder.Color->notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending->{
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date  -> notes.sortedByDescending   { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending  { it.color }
                    }
                }
            }
        }
    }
}