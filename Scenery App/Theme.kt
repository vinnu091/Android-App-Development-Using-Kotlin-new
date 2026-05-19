package com.example.sceneryapp.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable



private val LightColors = lightColorScheme()
private val DarkColors = darkColorScheme()

@Composable
fun MoodAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
