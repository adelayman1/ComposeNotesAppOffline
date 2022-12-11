package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.models.NoteModel
import com.example.noteappcompose.domain.repositories.NoteRepository
import com.example.noteappcompose.domain.utilitites.InvalidNoteIdException
import javax.inject.Inject

class GetNoteDetailsUseCase @Inject constructor(var noteRepository: NoteRepository) {
    public suspend operator fun invoke(noteId:Int): NoteModel {
        if (noteId<0) {
            throw InvalidNoteIdException()
        }
        return noteRepository.getNote(noteId) ?: throw InvalidNoteIdException()
    }
}