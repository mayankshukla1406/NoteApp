package com.example.noteapp.note_feature.presentation.notes.components

import android.util.Log
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.note_feature.domain.model.Note
import com.example.noteapp.note_feature.domain.use_case.NoteUseCases
import com.example.noteapp.note_feature.domain.utility.NoteOrder
import com.example.noteapp.note_feature.domain.utility.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel(){

    private val mutableState = mutableStateOf(NotesState())
    val state : State<NotesState> = mutableState
    private var recentDeletedNote : Note? = null
    private var getAllNotesJob : Job? = null


    init {
        getAllNotes(NoteOrder.Date(OrderType.Descending))
    }
    fun onEvent(event : NotesEvent)
    {
        when(event)
        {
            is NotesEvent.Order -> {
                if(state.value.noteOrder::class == event.noteOrder.orderType::class
                    && state.value.noteOrder.orderType == event.noteOrder.orderType)
                {
                    return
                }
                Log.d("viewModel",getAllNotes(event.noteOrder).toString())
                getAllNotes(event.noteOrder)
        }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentDeletedNote = event.note
                }

            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(
                        recentDeletedNote ?: return@launch)
                    recentDeletedNote = null
                }

            }
            is NotesEvent.ToggleOrderSection -> {
                mutableState.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getAllNotes(noteOrder: NoteOrder) {
        getAllNotesJob?.cancel()
        getAllNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach {notes->
                Log.d("kotlinUnit",notes.toString())
                mutableState.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}