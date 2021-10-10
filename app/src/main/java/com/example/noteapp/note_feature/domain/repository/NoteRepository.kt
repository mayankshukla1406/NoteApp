package com.example.noteapp.note_feature.domain.repository

import com.example.noteapp.note_feature.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes():Flow<List<Note>>

    suspend fun getNoteById(id:Int):Note?

    suspend fun insertNote(note:Note)

    suspend fun deleteNote(note:Note)
}