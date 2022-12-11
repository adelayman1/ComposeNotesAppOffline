package com.example.noteappcompose.presentation.noteDetailsScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappcompose.domain.models.NoteModel
import com.example.noteappcompose.domain.usecases.AddNoteUseCase
import com.example.noteappcompose.domain.usecases.DeleteNoteUseCase
import com.example.noteappcompose.domain.usecases.GetNoteDetailsUseCase
import com.example.noteappcompose.domain.usecases.SearchNotesUseCase
import com.example.noteappcompose.domain.usecases.ValidateNoteDescriptionUseCase
import com.example.noteappcompose.domain.usecases.ValidateNoteSubtitleUseCase
import com.example.noteappcompose.domain.usecases.ValidateNoteTitleUseCase
import com.example.noteappcompose.domain.usecases.ValidateWebLinkUseCase
import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.LinkUiState
import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.NoteDetailsEvent
import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.NoteDetailsUiState
import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    var addNoteUseCase: AddNoteUseCase,
    var getNoteDetailsUseCase: GetNoteDetailsUseCase,
    var validateWebLinkUseCase: ValidateWebLinkUseCase,
    var validateNoteTitleUseCase: ValidateNoteTitleUseCase,
    var validateNoteSubtitleUseCase: ValidateNoteSubtitleUseCase,
    var validateNoteDescriptionUseCase: ValidateNoteDescriptionUseCase,
    var deleteNoteUseCase: DeleteNoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var noteDetailsUiState by mutableStateOf(NoteDetailsUiState())
        private set;

    var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    getNoteDetailsUseCase(noteId).also { note ->
                        Log.d("TAGTAGTAGTAGTAGTAGTAGTAGTAG", ":${note.imagePath}")
                        val linkUiState = LinkUiState(
                            finalLink = note.webLink,
                            isLinkVisible = !note.webLink.isNullOrBlank(),
                        )
                        noteDetailsUiState = noteDetailsUiState.copy(
                            id = noteId,
                            titleInputFieldUiState = noteDetailsUiState.titleInputFieldUiState.copy(
                                text = note.noteTitle
                            ),
                            subtitleInputFieldUiState = noteDetailsUiState.subtitleInputFieldUiState.copy(
                                text = note.noteSubtitle
                            ),
                            descriptionInputFieldUiState = noteDetailsUiState.descriptionInputFieldUiState.copy(
                                text = note.noteText
                            ),
                            dateTime = note.dateTime,
                            linkUiState = linkUiState,
                            imagePath = note.imagePath,
                            isImageVisible = !note.imagePath.isNullOrBlank(),
                            noteColor = note.noteColor,
                        )
                    }
                }
            }
        }
    }

    fun onEvent(action: NoteDetailsEvent) {
        when (action) {
            is NoteDetailsEvent.TitleChanged -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    titleInputFieldUiState = noteDetailsUiState.titleInputFieldUiState.copy(
                        text = action.text,
                        errorMessage = null
                    )
                )

            is NoteDetailsEvent.SubtitleChanged -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    subtitleInputFieldUiState = noteDetailsUiState.subtitleInputFieldUiState.copy(
                        text = action.text,
                        errorMessage = null
                    )
                )

            is NoteDetailsEvent.NoteTextChanged -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    descriptionInputFieldUiState = noteDetailsUiState.descriptionInputFieldUiState.copy(
                        text = action.text,
                        errorMessage = null
                    )
                )

            is NoteDetailsEvent.ClickColor -> {
                noteDetailsUiState = noteDetailsUiState.copy(
                    noteColor = action.color,
                )
            }

            is NoteDetailsEvent.SaveNote -> {
                viewModelScope.launch {
                    val titleValidationResult =
                        validateNoteTitleUseCase(noteDetailsUiState.titleInputFieldUiState.text)
                    val subtitleValidationResult =
                        validateNoteSubtitleUseCase(noteDetailsUiState.subtitleInputFieldUiState.text)
                    val descriptionValidationResult =
                        validateNoteDescriptionUseCase(noteDetailsUiState.descriptionInputFieldUiState.text)
                    val hasValidationError = listOf(
                        titleValidationResult,
                        subtitleValidationResult,
                        descriptionValidationResult
                    ).any { it.error != null }
                    if (hasValidationError) {
                        noteDetailsUiState = noteDetailsUiState.copy(
                            titleInputFieldUiState = noteDetailsUiState.titleInputFieldUiState.copy(
                                errorMessage = titleValidationResult.error
                            ),
                            subtitleInputFieldUiState = noteDetailsUiState.subtitleInputFieldUiState.copy(
                                errorMessage = subtitleValidationResult.error
                            ),
                            descriptionInputFieldUiState = noteDetailsUiState.descriptionInputFieldUiState.copy(
                                errorMessage = descriptionValidationResult.error
                            )
                        )
                    } else {
                        addNoteUseCase(
                            NoteModel(
                                id = if(noteDetailsUiState.id == -1) null else noteDetailsUiState.id,
                                noteTitle = noteDetailsUiState.titleInputFieldUiState.text,
                                noteSubtitle = noteDetailsUiState.subtitleInputFieldUiState.text,
                                noteText = noteDetailsUiState.descriptionInputFieldUiState.text,
                                dateTime = noteDetailsUiState.dateTime,
                                imagePath = noteDetailsUiState.imagePath,
                                noteColor = noteDetailsUiState.noteColor,
                                webLink = noteDetailsUiState.linkUiState.finalLink
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }
                }
            }

            is NoteDetailsEvent.AddUrlDialog -> {
                val webLinkValidationResult =
                    validateWebLinkUseCase(webLink = noteDetailsUiState.linkUiState.typedLink)
                if (webLinkValidationResult.error == null) {
                    noteDetailsUiState =
                        noteDetailsUiState.copy(
                            linkUiState = noteDetailsUiState.linkUiState.copy(
                                isLinkDialogVisible = false,
                                finalLink = noteDetailsUiState.linkUiState.typedLink,
                                isLinkVisible = noteDetailsUiState.linkUiState.typedLink.isNotBlank(),
                                linkError = null
                            ),
                        )
                } else {
                    noteDetailsUiState =
                        noteDetailsUiState.copy(
                            linkUiState = noteDetailsUiState.linkUiState.copy(
                                linkError = webLinkValidationResult.error
                            ),
                        )
                }
            }

            is NoteDetailsEvent.DismissUrlDialog -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    linkUiState = noteDetailsUiState.linkUiState.copy(
                        isLinkDialogVisible = false,
                        linkError = null
                    ),
                )

            is NoteDetailsEvent.UrlTextChanged -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    linkUiState = noteDetailsUiState.linkUiState.copy(
                        typedLink = action.text,
                        linkError = null,
                    ),
                )

            NoteDetailsEvent.ShowUrlDialog -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    linkUiState = noteDetailsUiState.linkUiState.copy(
                        isLinkDialogVisible = true
                    ),
                )

            NoteDetailsEvent.DeleteUrl -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    linkUiState = noteDetailsUiState.linkUiState.copy(
                        finalLink = null,
                        isLinkVisible = false
                    ),
                )

            is NoteDetailsEvent.SelectedImage -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    imagePath = action.uri.toString(),
                    isImageVisible = true
                )

            NoteDetailsEvent.DeleteImage -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    isImageVisible = false,
                    imagePath = null
                )

            NoteDetailsEvent.DeleteNote -> {
                viewModelScope.launch {
                    runBlocking {
                        deleteNoteUseCase(noteDetailsUiState.id)
                    }
                    _eventFlow.emit(UiEvent.DeleteNote)
                }
            }
        }
    }
}