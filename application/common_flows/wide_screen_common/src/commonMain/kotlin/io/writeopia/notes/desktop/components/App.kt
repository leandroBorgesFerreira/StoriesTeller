package io.writeopia.notes.desktop.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.unit.dp
import io.writeopia.auth.core.di.KmpAuthCoreInjection
import io.writeopia.auth.core.token.MockTokenHandler
import io.writeopia.editor.di.EditorKmpInjector
import io.writeopia.editor.ui.desktop.AppTextEditor
import io.writeopia.editor.ui.desktop.EditorScaffold
import io.writeopia.navigation.Navigation
import io.writeopia.note_menu.di.NotesConfigurationInjector
import io.writeopia.note_menu.di.NotesMenuKmpInjection
import io.writeopia.note_menu.ui.screen.DesktopNotesMenu
import io.writeopia.notes.desktop.components.navigation.NavigationPage
import io.writeopia.notes.desktop.components.navigation.NavigationViewModel
import io.writeopia.sdk.network.injector.ConnectionInjector
import io.writeopia.sdk.persistence.core.di.RepositoryInjector
import io.writeopia.theme.WrieopiaTheme
import io.writeopia.ui.drawer.factory.DrawersFactory
import io.writeopia.utils_module.Destinations
import kotlinx.coroutines.flow.StateFlow

@Composable
fun App(
    notesConfigurationInjector: NotesConfigurationInjector,
    repositoryInjection: RepositoryInjector,
    drawersFactory: DrawersFactory,
    disableWebsocket: Boolean = false,
    isUndoKeyEvent: (KeyEvent) -> Boolean,
    selectionState: StateFlow<Boolean>,
) {
    val authCoreInjection = KmpAuthCoreInjection()
    val connectionInjection =
        ConnectionInjector(
            bearerTokenHandler = MockTokenHandler,
            baseUrl = "https://writeopia.io/api",
            disableWebsocket = disableWebsocket
        )

    val editorInjector = EditorKmpInjector(
        authCoreInjection = authCoreInjection,
        repositoryInjection = repositoryInjection,
        connectionInjection = connectionInjection
    )

    val notesMenuInjection = NotesMenuKmpInjection(
        notesConfigurationInjector = notesConfigurationInjector,
        authCoreInjection = authCoreInjection,
        repositoryInjection = repositoryInjection,
        selectionState = selectionState,
    )

    WrieopiaTheme {
        Navigation(
            startDestination = Destinations.CHOOSE_NOTE.id,
            notesMenuInjection = notesMenuInjection,
            editorInjector = editorInjector
        )
//        val currentPage by navigationViewModel.navigationPage.collectAsState()
//        val backgroundColor = MaterialTheme.colorScheme.background
//
//        Crossfade(currentPage, animationSpec = tween(durationMillis = 300)) { state ->
//            when (state) {
//                NavigationPage.NoteMenu -> {
//                    val coroutineScope = rememberCoroutineScope()
//                    val chooseNoteViewModel =
//                        notesMenuInjection.provideChooseNoteViewModel(coroutineScope)
//
//                    DesktopNotesMenu(
//                        chooseNoteViewModel = chooseNoteViewModel,
//                        onNewNoteClick = {
//                            navigationViewModel.navigateTo(NavigationPage.Editor())
//                        },
//                        onNoteClick = { id, title ->
//                            val isHandled = chooseNoteViewModel.handleNoteTap(id)
//
//                            if (!isHandled) {
//                                navigationViewModel.navigateTo(NavigationPage.Editor(id, title))
//                            }
//                        },
//                        modifier = Modifier.background(backgroundColor)
//                    )
//                }
//
//                is NavigationPage.Editor -> {
//                    val noteEditorViewModel = remember {
//                        editorInjector.provideNoteEditorViewModel()
//                    }
//
//                    EditorScaffold(
//                        clickAtBottom = noteEditorViewModel.writeopiaManager::clickAtTheEnd,
//                        onBackClick = {
//                            navigationViewModel.navigateTo(NavigationPage.NoteMenu)
//                        },
//                        modifier = Modifier.background(backgroundColor),
//                        content = {
//                            AppTextEditor(
//                                noteEditorViewModel.writeopiaManager,
//                                noteEditorViewModel,
//                                drawersFactory = drawersFactory,
//                                loadNoteId = state.noteId,
//                                modifier = Modifier.onPreviewKeyEvent { keyEvent ->
//                                    if (isUndoKeyEvent(keyEvent)) {
//                                        noteEditorViewModel.undo()
//                                        true
//                                    } else {
//                                        false
//                                    }
//                                }.padding(horizontal = 30.dp)
//                            )
//                        }
//                    )
//                }
//            }
//        }
    }
}
