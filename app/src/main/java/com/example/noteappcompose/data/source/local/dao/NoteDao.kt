package com.example.noteappcompose.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteappcompose.data.source.local.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE note_text LIKE '%' || :search || '%' OR subtitle LIKE '%' || :search || '%' OR note_text LIKE '%' || :search || '%'")
    fun search(search:String): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}