package com.github.leandroborgesferreira.storyteller.uicomponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

// This screen could live in a module for extra Composables
@Composable
fun BoxScope.HeaderEdition(
    modifier: Modifier = Modifier,
    availableColors: List<Color>,
    onColorSelection: (Color?) -> Unit,
    visibilityState: Boolean,
    outsideClick: () -> Unit,
) {
    val cellSpacing = 2.dp
    val colors = listOf(Color.Transparent) + availableColors
    val shape = RoundedCornerShape(9.dp)
    val topCorner = CornerSize(16.dp)
    val bottomCorner = CornerSize(0.dp)

    SlideInBox(
        modifier = modifier.align(Alignment.BottomCenter),
        editState = visibilityState,
        outsideClick = outsideClick,
        animationLabel = "HeaderEditionAnimation"
    ) {
        LazyVerticalGrid(
            modifier = modifier
                .clip(
                    RoundedCornerShape(
                        topCorner,
                        topCorner,
                        bottomCorner,
                        bottomCorner
                    )
                )
                .background(MaterialTheme.colorScheme.surface)
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(cellSpacing),
            verticalArrangement = Arrangement.spacedBy(cellSpacing),
            columns = GridCells.Adaptive(minSize = 100.dp)
        ) {
            itemsIndexed(colors) { i, color ->
                Box(
                    modifier = Modifier
                        .clickable {
                            onColorSelection(if (i == 0) null else color)
                        }
                        .border(BorderStroke(1.dp, Color.Black), shape = shape)
                        .clip(shape)
                        .height(70.dp)
                        .width(110.dp)
                        .background(color)
                ) {
                    if (i == 0) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(40.dp),
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = "Remove color",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HeaderEditionPreview() {
    val colors = listOf(Color.Blue, Color.White, Color.Cyan, Color.LightGray, Color.Red, Color.Blue)

    Box(modifier = Modifier.background(Color.White)) {
        HeaderEdition(
            availableColors = colors,
            onColorSelection = {},
            outsideClick = {},
            visibilityState = true
        )
    }
}

//Todo: Delete this!!
@Composable
fun SlideInBox(
    modifier: Modifier = Modifier,
    editState: Boolean,
    animationLabel: String,
    outsideClick: () -> Unit,
    showBackground: Boolean = true,
    enterAnimationSpec: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    ),
    exitAnimationSpec: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    ),
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = editState && showBackground,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xAA000000))
                .clickable(onClick = outsideClick)
        )
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = editState,
        label = animationLabel,
        enter = slideInVertically(
            animationSpec = enterAnimationSpec,
            initialOffsetY = { fullHeight -> fullHeight }
        ),
        exit = slideOutVertically(
            animationSpec = exitAnimationSpec,
            targetOffsetY = { fullHeight -> fullHeight }
        ),
        content = content
    )
}