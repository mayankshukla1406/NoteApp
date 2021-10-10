package com.example.noteapp.note_feature.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    data class UserEnteredTitle(val value : String) : AddEditNoteEvent()
    data class UserEnteredContent(val value : String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusStateTitle : FocusState) : AddEditNoteEvent()
    data class ChangeContentFocus(val focusStateContent : FocusState) : AddEditNoteEvent()
    data class ChangeColor(val color : Int) : AddEditNoteEvent()
    object SaveNote:AddEditNoteEvent()

}
