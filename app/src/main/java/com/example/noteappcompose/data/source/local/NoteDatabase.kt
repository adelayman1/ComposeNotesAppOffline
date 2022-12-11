package com.example.noteappcompose.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteappcompose.data.source.local.dao.NoteDao
import com.example.noteappcompose.data.source.local.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    companion object {
        const val DATABASE_NAME = "note_db"
    }
}
