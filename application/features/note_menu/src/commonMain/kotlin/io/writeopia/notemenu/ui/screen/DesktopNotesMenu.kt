package io.writeopia.notemenu.ui.screen

import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.writeopia.common.utils.file.fileChooserSave
import io.writeopia.common.utils.icons.WrIcons
import io.writeopia.notemenu.data.model.NotesNavigation
import io.writeopia.notemenu.ui.screen.actions.DesktopNoteActionsMenu
import io.writeopia.notemenu.ui.screen.configuration.molecules.NotesConfigurationMenu
import io.writeopia.notemenu.ui.screen.configuration.molecules.NotesSelectionMenu
import io.writeopia.notemenu.ui.screen.configuration.molecules.WorkspaceConfigurationDialog
import io.writeopia.notemenu.ui.screen.documents.NotesCards
import io.writeopia.notemenu.ui.screen.file.fileChooserLoad
import io.writeopia.notemenu.viewmodel.ChooseNoteViewModel
import io.writeopia.notemenu.viewmodel.ConfigState
import io.writeopia.notemenu.viewmodel.getPath
import io.writeopia.notemenu.viewmodel.toNumberDesktop

@Composable
fun DesktopNotesMenu(
    chooseNoteViewModel: ChooseNoteViewModel,
    onNewNoteClick: () -> Unit,
    onNoteClick: (String, String) -> Unit,
    navigateToNotes: (NotesNavigation) -> Unit,
//    addFolder: () -> Unit,
//    editFolder: (MenuItemUi.FolderUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(
        key1 = "refresh",
        block = {
            chooseNoteViewModel.requestUser()
        }
    )

    val borderPadding = 8.dp

    Box(modifier = modifier.fillMaxSize().padding(end = 12.dp)) {
        Column(modifier = Modifier.padding(top = borderPadding)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.weight(1F))

                DesktopNoteActionsMenu(
                    showExtraOptions = chooseNoteViewModel.editState,
                    showExtraOptionsRequest = chooseNoteViewModel::showEditMenu,
                    hideExtraOptionsRequest = chooseNoteViewModel::cancelEditMenu,
                    configureDirectory = chooseNoteViewModel::configureDirectory,
                    exportAsMarkdownClick = {
                        fileChooserSave("")?.let(chooseNoteViewModel::directoryFilesAsMarkdown)
                    },
                    importClick = {
                        chooseNoteViewModel.loadFiles(fileChooserLoad(""))
                    },
                    syncInProgressState = chooseNoteViewModel.syncInProgress,
                    onSyncLocallySelected = chooseNoteViewModel::onSyncLocallySelected,
                    onWriteLocallySelected = chooseNoteViewModel::onWriteLocallySelected,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 40.dp)
            ) {
                NotesCards(
                    documents = chooseNoteViewModel.documentsState.collectAsState().value,
                    loadNote = { id, title ->
                        val handled = chooseNoteViewModel.handleMenuItemTap(id)
                        if (!handled) {
                            onNoteClick(id, title)
                        }
                    },
                    selectionListener = chooseNoteViewModel::onDocumentSelected,
                    folderClick = { id ->
                        val handled = chooseNoteViewModel.handleMenuItemTap(id)
                        if (!handled) {
                            navigateToNotes(NotesNavigation.Folder(id))
                        }
                    },
                    moveRequest = { item, parentId ->
                        chooseNoteViewModel.moveToFolder(item, parentId)
                    },
                    modifier = Modifier.weight(1F).fillMaxHeight(),
                    changeIcon = chooseNoteViewModel::changeIcons
                )

                Spacer(modifier = Modifier.width(20.dp))

                NotesConfigurationMenu(
                    modifier = Modifier.padding(end = borderPadding),
                    showSortingOption = chooseNoteViewModel.showSortMenuState,
                    selectedState = chooseNoteViewModel.notesArrangement.toNumberDesktop(),
                    showSortOptionsRequest = chooseNoteViewModel::showSortMenu,
                    hideSortOptionsRequest = chooseNoteViewModel::cancelSortMenu,
                    staggeredGridSelected =
                        chooseNoteViewModel::staggeredGridArrangementSelected,
                    gridSelected = chooseNoteViewModel::gridArrangementSelected,
                    listSelected = chooseNoteViewModel::listArrangementSelected,
                    selectSortOption = chooseNoteViewModel::sortingSelected,
                    sortOptionState = chooseNoteViewModel.orderByState
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(horizontal = 40.dp - borderPadding, vertical = 40.dp)
                .testTag("addNote"),
            onClick = onNewNoteClick,
            content = {
                Icon(
                    imageVector = WrIcons.add,
                    contentDescription = "New note",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            containerColor = MaterialTheme.colorScheme.primary
        )

        val configState = chooseNoteViewModel.showLocalSyncConfigState.collectAsState().value

        if (configState != ConfigState.Idle) {
            WorkspaceConfigurationDialog(
                currentPath = configState.getPath(),
                pathChange = chooseNoteViewModel::pathSelected,
                onDismissRequest = chooseNoteViewModel::hideConfigSyncMenu,
                onConfirmation = chooseNoteViewModel::confirmWorkplacePath
            )
        }

        val hasSelectedNotes by chooseNoteViewModel.hasSelectedNotes.collectAsState()

        NotesSelectionMenu(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 40.dp).width(400.dp),
            visibilityState = hasSelectedNotes,
            onDelete = chooseNoteViewModel::deleteSelectedNotes,
            onCopy = chooseNoteViewModel::copySelectedNotes,
            onFavorite = chooseNoteViewModel::favoriteSelectedNotes,
            onClose = chooseNoteViewModel::unSelectNotes,
            shape = RoundedCornerShape(CornerSize(16.dp)),
            exitAnimationOffset = 2.3F,
            enterAnimationSpec = spring(dampingRatio = 0.6F)
        )
    }
}
