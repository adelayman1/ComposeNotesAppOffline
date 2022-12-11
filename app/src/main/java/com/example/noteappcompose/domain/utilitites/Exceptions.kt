package com.example.noteappcompose.domain.utilitites

class InvalidNoteTitleException(): Exception("Title is not valid")
class InvalidNoteSubtitleException(): Exception("Subtitle is not valid")
class InvalidNoteTextException: Exception("Text is not valid")
class InvalidNoteIdException: Exception("ID is not valid")