package com.example.noteappcompose.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteappcompose.domain.models.NoteModel

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "note_text")
    val noteText: String,
    @ColumnInfo(name = "date_time")
    val dateTime: String,
    @ColumnInfo(name = "image_path", defaultValue = "")
    val imagePath: String?,
    @ColumnInfo(name = "color")
    val color: Int,
    @ColumnInfo(name = "web_link", defaultValue = "")
    val webLink: String?,
) {
    fun toNoteModel(): NoteModel = NoteModel(
        id = id,
        noteTitle = title,
        noteSubtitle = subtitle,
        noteText = noteText,
        dateTime = dateTime,
        imagePath = imagePath,
        noteColor = color,
        webLink = webLink
    )
}