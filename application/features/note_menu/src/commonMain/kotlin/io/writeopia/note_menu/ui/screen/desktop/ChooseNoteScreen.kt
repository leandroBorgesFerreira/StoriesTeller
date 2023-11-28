package io.writeopia.note_menu.ui.screen.desktop

import androidx.compose.runtime.Composable
import io.writeopia.note_menu.ui.components.menu.Notes
import io.writeopia.note_menu.viewmodel.ChooseNoteViewModel

@Composable
fun ChooseNoteScreen(
    chooseNoteViewModel: ChooseNoteViewModel,
    loadNote: (String, String) -> Unit,
    newNote: () -> Unit,
) {
    Notes(
        documentsState = chooseNoteViewModel.documentsState,
        notesArrangement = chooseNoteViewModel.notesArrangement,
        loadNote = loadNote,
        selectionListener = chooseNoteViewModel::onDocumentSelected
    )
}