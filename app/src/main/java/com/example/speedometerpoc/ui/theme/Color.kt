package com.example.speedometerpoc.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val DarkColor = Color(0xFF101522)
val DarkColor2 = Color(0xFF202532)
val LightColor = Color(0xFF414D66)
val LightColor2 = Color(0xFF626F88)


val _RED = Color(0xFFcf352e)
val _YELLOW = Color(0xFFffa500)
val _GREEN = Color(0xFF65f057)
val GrayGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF686868), Color(0xFFa0a0a0)),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)


val GreenGradient = Brush.linearGradient(
    colors = listOf(_GREEN, _YELLOW, _YELLOW, _RED),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

val DarkGradient = Brush.verticalGradient(
    colors = listOf(DarkColor2, DarkColor)
)
val LightGradient = Brush.verticalGradient(
    colors = listOf(LightColor2, LightColor)
)