package io.writeopia.note_menu.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import io.writeopia.note_menu.di.NotesMenuInjection
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.writeopia.note_menu.ui.screen.menu.ChooseNoteScreen
import io.writeopia.utils_module.Destinations

fun NavGraphBuilder.notesMenuNavigation(
    notesMenuInjection: NotesMenuInjection,
    navigateToNote: (String, String) -> Unit,
    navigateToNewNote: () -> Unit,
    navigateToAccount: () -> Unit,
) {

    composable(
        Destinations.CHOOSE_NOTE.id,
        enterTransition = {
            val isFirstScreen = this.initialState.destination
                .route
                ?.startsWith(Destinations.AUTH_MENU.id)

            if (isFirstScreen == true) {
                fadeIn()
            } else {
                slideInHorizontally(initialOffsetX = { intSize -> -intSize })
            }
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { intSize -> -intSize })
        }
    ) {
        val chooseNoteViewModel = notesMenuInjection.provideChooseNoteViewModel()

        ChooseNoteScreen(
            chooseNoteViewModel = chooseNoteViewModel,
            loadNote = navigateToNote,
            goToAccount = navigateToAccount,
            newNote = navigateToNewNote,
        )
    }
}
