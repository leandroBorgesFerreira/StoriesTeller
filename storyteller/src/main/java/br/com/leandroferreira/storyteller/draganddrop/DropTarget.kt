package br.com.leandroferreira.storyteller.draganddrop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned

@Composable
fun DropTarget(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(inBound: Boolean, data: Any?) -> Unit
) {

    val dragInfo: DragTargetInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember { mutableStateOf(false) }

    Box(modifier = modifier.onGloballyPositioned { layoutCoordinates ->
        layoutCoordinates.boundsInWindow().let { rect ->
            isCurrentDropTarget = rect.contains(dragPosition + dragOffset)
        }
    }) {
        val data =
            if (isCurrentDropTarget && !dragInfo.isDragging) dragInfo.dataToDrop else null

        if (data != null) {
            content(isCurrentDropTarget, data)
            dragInfo.dataToDrop = null
        } else {
            content(isCurrentDropTarget,null)
        }
    }
}
