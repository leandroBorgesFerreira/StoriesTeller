package com.storiesteller.note_menu.di

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.storiesteller.sdk.manager.DocumentRepository
import com.storiesteller.sdk.persistence.database.StoriesTellerDatabase
import com.storiesteller.sdk.persistence.repository.DocumentRepositoryImpl
import com.storiesteller.auth.core.AuthManager
import com.storiesteller.note_menu.data.usecase.NotesConfigurationRepository
import com.storiesteller.note_menu.data.usecase.NotesUseCase
import com.storiesteller.note_menu.viewmodel.ChooseNoteViewModel

class NotesMenuInjection(
    private val database: StoriesTellerDatabase,
    private val sharedPreferences: SharedPreferences,
    private val authManager: AuthManager,
) {

    private fun provideDocumentRepository(): DocumentRepository =
        DocumentRepositoryImpl(
            database.documentDao(),
            database.storyUnitDao()
        )

    private fun provideNotesConfigurationRepository(): NotesConfigurationRepository =
        NotesConfigurationRepository(sharedPreferences)

    private fun provideNotesUseCase(
        documentRepository: DocumentRepository = provideDocumentRepository(),
        notesConfigurationRepository: NotesConfigurationRepository =
            provideNotesConfigurationRepository()
    ): NotesUseCase {
        return NotesUseCase(documentRepository, notesConfigurationRepository)
    }

    @Composable
    internal fun provideChooseNoteViewModel(
        notesUseCase: NotesUseCase = provideNotesUseCase(),
        notesConfig: NotesConfigurationRepository = provideNotesConfigurationRepository()
    ): ChooseNoteViewModel {
        return viewModel(
            factory = ChooseNoteViewModelFactory(
                notesUseCase,
                notesConfig,
                authManager
            )
        )
    }
}
