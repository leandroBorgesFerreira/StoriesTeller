package io.writeopia.notes.desktop.components

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import io.writeopia.editor.di.EditorKmpInjector
import io.writeopia.editor.ui.TextEditor
import io.writeopia.sdk.drawer.factory.DefaultDrawersDesktop
import io.writeopia.sdk.manager.WriteopiaManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun AppTextEditor(manager: WriteopiaManager, editorKmpInjector: EditorKmpInjector) {
    val listState: LazyListState = rememberLazyListState()
    val coroutine = rememberCoroutineScope()

    coroutine.launch {
        manager.scrollToPosition.collectLatest {
            listState.animateScrollBy(70F)
        }
    }

    val viewModel = editorKmpInjector.provideNoteDetailsViewModel()
    viewModel.initCoroutine(coroutine)
    manager.newStory()

    Column(modifier = Modifier.fillMaxWidth()) {
        TextEditor(viewModel, DefaultDrawersDesktop)
    }
}
