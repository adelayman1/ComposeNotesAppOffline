package com.example.noteappcompose.presentation.utility

import androidx.compose.ui.graphics.Color
import com.example.noteappcompose.ui.theme.Blue
import com.example.noteappcompose.ui.theme.Orange
import com.example.noteappcompose.ui.theme.Red
import com.example.noteappcompose.ui.theme.SearchGray
import javax.inject.Singleton

@Singleton
class Constants {
    companion object{
        val noteColorsList = listOf(SearchGray, Orange, Red, Blue, Color.Black)
    }
}