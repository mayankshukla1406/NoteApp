package com.example.noteapp.note_feature.presentation.utility

sealed class Screens(val route : String) {
    object NotesScreen:Screens("notesScreen")
    object AddEditNoteScreen:Screens("addEditNoteScreen")
}
