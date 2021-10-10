package com.example.noteapp.note_feature.presentation.add_edit_note.components

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.note_feature.domain.model.InvalidAddNoteException
import com.example.noteapp.note_feature.domain.model.Note
import com.example.noteapp.note_feature.domain.use_case.NoteUseCases
import com.example.noteapp.note_feature.presentation.add_edit_note.AddEditNoteEvent
import com.example.noteapp.note_feature.presentation.add_edit_note.NoteTextFieldHintState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val noteTitleState = mutableStateOf(NoteTextFieldHintState(
        hint = "Enter Title For Your Note..."
    ))

    val noteTitle : State<NoteTextFieldHintState> = noteTitleState

    private val noteContentState = mutableStateOf(NoteTextFieldHintState(
        hint = "Enter Content for Your Note..."
    ))
    val noteContent : State<NoteTextFieldHintState> = noteContentState

    private val noteColorState = mutableStateOf(Note.noteColorsList.random().toArgb())
    val noteColor : State<Int> = noteColorState

    private val eventFlowState = MutableSharedFlow<UIEvent>()
    val eventFlow = eventFlowState.asSharedFlow()

    private var currentNoteID : Int? = null


    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId->
            if(noteId!=-1)
            {
                viewModelScope.launch {
                    noteUseCases.getNoteById(noteId)?.also { note->
                        currentNoteID = note.id
                        noteTitleState.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        noteContentState.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        noteColorState.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event:AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.UserEnteredTitle -> {
                noteTitleState.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.UserEnteredContent -> {
                noteContentState.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus ->{
                noteTitleState.value = noteTitle.value.copy(
                    isHintVisible = !event.focusStateTitle.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeContentFocus->{
                noteContentState.value = noteContent.value.copy(
                    isHintVisible = !event.focusStateContent.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor ->{
                noteColorState.value = event.color
            }
            is AddEditNoteEvent.SaveNote->{
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteID
                            )
                        )
                        eventFlowState.emit(UIEvent.SaveNote)
                    } catch (e : InvalidAddNoteException){
                        eventFlowState.emit(
                            UIEvent.displaySnackBar(message = e.message ?: "Could not save note")
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent{
        data class displaySnackBar(val message : String):UIEvent()
        object SaveNote : UIEvent()
    }
}