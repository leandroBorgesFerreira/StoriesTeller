package io.writeopia.ui.drawer.factory

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import io.writeopia.ui.drawer.StoryStepDrawer
import io.writeopia.ui.manager.WriteopiaStateManager
import io.writeopia.ui.model.DrawConfig

object DefaultDrawersNative : DrawersFactory {

    @Composable
    override fun create(
        manager: WriteopiaStateManager,
        defaultBorder: Shape,
        editable: Boolean,
        groupsBackgroundColor: Color,
        onHeaderClick: () -> Unit,
        selectedColor: Color,
        selectedBorderColor: Color,
        fontFamily: FontFamily?,
    ): Map<Int, StoryStepDrawer> {
        val commonDrawers = CommonDrawers.create(
            manager,
            marginAtBottom = 500.dp,
            defaultBorder,
            editable,
            onHeaderClick,
            lineBreakByContent = true,
            drawConfig = DrawConfig(
                textDrawerStartPadding = 2,
                textVerticalPadding = 4,
                codeBlockStartPadding = 0,
                checkBoxStartPadding = 0,
                checkBoxEndPadding = 0,
                checkBoxItemVerticalPadding = 0,
                listItemStartPadding = 8,
                selectedColor = { selectedColor },
                selectedBorderColor = { selectedBorderColor }
            ),
            isDesktop = false,
            eventListener = KeyEventListenerFactory.android(
                manager,
                isEmptyErase = { _, _ -> false }
            )
        )

        return commonDrawers
    }

//    private fun emptyErase(
//        keyEvent: androidx.compose.ui.input.key.KeyEvent,
//        input: TextFieldValue
//    ): Boolean =
//        keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL && input.selection.start == 0
}
