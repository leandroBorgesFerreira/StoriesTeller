package io.writeopia.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import io.writeopia.sdk.models.story.StoryStep
import io.writeopia.sdk.models.story.Tag

@Composable
fun defaultTextStyle(storyStep: StoryStep) =
    TextStyle(
        textDecoration = if (storyStep.checked == true) TextDecoration.LineThrough else null,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = textSizeFromTags(storyStep.tags.map { it.tag })
    )

@Composable
fun codeBlockStyle() =
    TextStyle(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 16.sp,
        fontFamily = FontFamily.Serif
    )

private fun textSizeFromTags(tags: Iterable<Tag>) =
    tags.firstOrNull { tag -> tag.isTitle() }
        ?.let(::textSizeFromTag)
        ?: 16.sp

private fun textSizeFromTag(tags: Tag) =
    when (tags) {
        Tag.H1 -> 32.sp
        Tag.H2 -> 28.sp
        Tag.H3 -> 24.sp
        Tag.H4 -> 20.sp
        else -> 20.sp
    }
