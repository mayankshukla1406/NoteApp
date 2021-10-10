package com.example.noteapp.di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.note_feature.data.data_source.NoteDatabase
import com.example.noteapp.note_feature.data.repository.NoteRepositoryImplementation
import com.example.noteapp.note_feature.domain.repository.NoteRepository
import com.example.noteapp.note_feature.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application):NoteDatabase{
        return Room.databaseBuilder(
            app,NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db:NoteDatabase):NoteRepository{
        return NoteRepositoryImplementation(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
            getNoteById = GetNoteByIdUseCase(repository)
        )
    }
}