package com.example.noteappcompose.presentation.noteDetailsScreen.uiStates

sealed class UiEvent{
    object SaveNote:UiEvent()
    object DeleteNote:UiEvent()
}
