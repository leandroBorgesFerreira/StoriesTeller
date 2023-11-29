package io.writeopia.note_menu.ui

import io.writeopia.note_menu.ui.dto.DocumentUi
import io.writeopia.sdk.models.story.StoryStep
import io.writeopia.sdk.models.story.StoryTypes

object NotesDummies {

    fun dummyDocumentUi() =
        DocumentUi(
            documentId = "documentId",
            title = "Title",
            lastEdit = "lastEdit",
            preview = dummyPreview(),
            selected = false,
        )

    private fun dummyPreview(): List<StoryStep> =
        listOf(
            StoryStep(type = StoryTypes.TITLE.type, text = "Title"),
            StoryStep(type = StoryTypes.CHECK_ITEM.type, text = "checkitem"),
            StoryStep(type = StoryTypes.H1.type, text = "H1"),
            StoryStep(type = StoryTypes.H2.type, text = "H2"),
            StoryStep(type = StoryTypes.H3.type, text = "H3"),
            StoryStep(type = StoryTypes.H4.type, text = "H4"),
            StoryStep(type = StoryTypes.TEXT.type, text = "text"),
            StoryStep(type = StoryTypes.UNORDERED_LIST_ITEM.type, text = "list item"),
        )
}