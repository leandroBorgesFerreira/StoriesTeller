package io.writeopia.notes.desktop

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import io.writeopia.auth.core.di.KmpAuthCoreInjection
import io.writeopia.editor.di.EditorKmpInjector
import io.writeopia.notes.desktop.components.Scaffold
import io.writeopia.notes.desktop.components.AppTextEditor
import io.writeopia.sdk.manager.WriteopiaManager
import io.writeopia.sdk.persistence.core.di.DaosInjector
import io.writeopia.sdk.persistence.core.tracker.OnUpdateDocumentTracker
import io.writeopia.sqldelight.create
import kotlinx.coroutines.Dispatchers

@Composable
fun App() {
    val authCoreInjection = KmpAuthCoreInjection()
    val daosInjection = DaosInjector.create()

    val editorInjetor = EditorKmpInjector(
        authCoreInjection = authCoreInjection,
        daosInjection = daosInjection
    )

    val writeopiaManager = WriteopiaManager(dispatcher = Dispatchers.IO).apply {
        saveOnStoryChanges(OnUpdateDocumentTracker(daosInjection.provideDocumentDao()))
    }

    MaterialTheme {
        Scaffold(clickAtBottom = writeopiaManager::clickAtTheEnd) {
            AppTextEditor(writeopiaManager, editorInjetor)
        }
    }
}
