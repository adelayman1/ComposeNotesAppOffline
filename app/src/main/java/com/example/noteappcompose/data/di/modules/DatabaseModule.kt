package com.example.noteappcompose.data.di.modules

import android.app.Application
import androidx.room.Room
import com.example.noteappcompose.data.source.local.NotesDatabase
import com.example.noteappcompose.data.source.local.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).build()
    }
    @Singleton
    @Provides
    fun provideNoteDao(notesDatabase: NotesDatabase): NoteDao {
        return notesDatabase.noteDao()
    }
}