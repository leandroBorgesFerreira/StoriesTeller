package io.writeopia.notes.desktop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import io.writeopia.notes.desktop.CreateTextEditor

@Composable
fun Scaffold(clickAtBottom: () -> Unit, content: ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        //Todo: Move this to WriteopiaEditorBox
        BoxWithConstraints {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .let { modifierLet ->
                        if (maxWidth > 900.dp) {
                            modifierLet.width(1000.dp)
                        } else {
                            modifierLet.fillMaxWidth()
                        }
                    }
                    .defaultMinSize(minHeight = 700.dp)
                    .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 30.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White),
            ) {
                content()

                Box(
                    modifier = Modifier.weight(1F)
                        .fillMaxWidth()
                        .clickable(
                            onClick = clickAtBottom,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        )
                )
            }
        }
    }
}
