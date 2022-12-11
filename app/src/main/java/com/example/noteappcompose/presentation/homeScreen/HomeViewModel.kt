package com.example.noteappcompose.presentation.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappcompose.domain.usecases.AddNoteUseCase
import com.example.noteappcompose.domain.usecases.GetNotesUseCase
import com.example.noteappcompose.domain.usecases.SearchNotesUseCase
import com.example.noteappcompose.presentation.homeScreen.uiStates.HomeUiEvent
import com.example.noteappcompose.presentation.homeScreen.uiStates.NoteItemUiState
import com.example.noteappcompose.presentation.homeScreen.uiStates.NotesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    var searchNotesUseCase: SearchNotesUseCase,
) : ViewModel() {
    var notesUiState by mutableStateOf(NotesUiState(isLoading = true))
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()
    init {
        getNotes()
    }

    private var getNotesJob: Job? = null
    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = viewModelScope.launch {
            getNotesUseCase().collectLatest { notes ->
                val notesListUiItems = notes.map { note ->
                    NoteItemUiState(id = note.id?:-2,
                        noteTitle = note.noteTitle,
                        noteSubtitle = note.noteSubtitle,
                        noteDate = note.dateTime,
                        noteColor = note.noteColor,
                        imagePath = note.imagePath,
                        isImageVisible = !note.imagePath.isNullOrBlank()
                    )
                }
                notesUiState = notesUiState.copy(
                    isLoading = false,
                    notes = notesListUiItems
                )
            }
        }
    }

    fun search() {
        viewModelScope.launch {
            if (notesUiState.searchText.trim().isBlank())
                notesUiState = notesUiState.copy(searchResult = emptyList())
            else {
                try {
                    val searchResult = searchNotesUseCase(notesUiState.searchText)
                    searchResult.collect{
                        val newSearchedList = it.map { note ->
                            NoteItemUiState(
                                id = note.id!!,
                                noteTitle = note.noteTitle,
                                noteSubtitle = note.noteSubtitle,
                                noteDate = note.dateTime,
                                noteColor = note.noteColor,
                                imagePath = note.imagePath,
                                isImageVisible = !note.imagePath.isNullOrBlank()
                            )
                        }
                        notesUiState = notesUiState.copy(searchResult = newSearchedList)
                    }
                } catch (e: Exception) {
                    _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                }
            }
        }
    }
    fun onEvent(action: HomeUiEvent) {
        when (action) {
            is HomeUiEvent.SearchTextChanged -> notesUiState =
                notesUiState.copy(searchText = action.text)

            HomeUiEvent.Search -> search()
        }
    }
    sealed class UiEvent {
        data class ShowMessage(var message: String) : UiEvent()
    }
}