package io.writeopia.notes.desktop

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Writeopia for Desktop",
        state = rememberWindowState(width = 1100.dp, height = 800.dp)
    ) {
        App()
    }
}
