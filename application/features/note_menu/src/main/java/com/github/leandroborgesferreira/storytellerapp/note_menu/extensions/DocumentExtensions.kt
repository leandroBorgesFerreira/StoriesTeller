package com.github.leandroborgesferreira.storytellerapp.note_menu.extensions

import com.github.leandroborgesferreira.storytellerapp.note_menu.ui.dto.DocumentUi
import com.storiesteller.sdk.models.document.Document
import com.storiesteller.sdk.preview.PreviewParser

internal fun Document.toUiCard(
    previewParser: PreviewParser,
    selected: Boolean = false
): DocumentUi =
    DocumentUi(
        documentId = id,
        title = title,
        lastEdit = "",
        preview = content.values.let(previewParser::preview),
        selected = selected
    )