package io.writeopia.note_menu.ui.components.configuration

//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.writeopia.common_ui.SlideInBox

private const val INNER_PADDING = 3

@Composable
internal fun BoxScope.ConfigurationsMenu(
    modifier: Modifier = Modifier,
    visibilityState: Boolean,
    outsideClick: () -> Unit,
    listOptionClick: () -> Unit,
    gridOptionClick: () -> Unit,
    sortingSelected: (io.writeopia.sdk.persistence.core.sorting.OrderBy) -> Unit,
) {
    SlideInBox(
        modifier = modifier.align(Alignment.BottomCenter),
        editState = visibilityState,
        outsideClick = outsideClick,
        animationLabel = "configurationsMenuAnimation"
    ) {
        val topCorner = CornerSize(16.dp)
        val bottomCorner = CornerSize(0.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topCorner,
                        topCorner,
                        bottomCorner,
                        bottomCorner
                    )
                )
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
        ) {
            ArrangementSection(listOptionClick, gridOptionClick)

            SortingSection(sortingSelected = sortingSelected)

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun SectionText(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 6.dp),
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 18.sp
        ),
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ArrangementOptions(listOptionClick: () -> Unit, gridOptionClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(INNER_PADDING.dp)
    ) {
        Icon(
            modifier = Modifier
                .orderConfigModifier(clickable = gridOptionClick)
                .weight(1F),
            imageVector = Icons.Outlined.Dashboard,
            contentDescription = "staggered card",
//            stringResource(R.string.staggered_card),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            modifier = Modifier
                .orderConfigModifier(clickable = listOptionClick)
                .weight(1F),
            imageVector = Icons.Outlined.List,
            contentDescription = "note list",
//            stringResource(R.string.note_list),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun ArrangementSection(listOptionClick: () -> Unit, gridOptionClick: () -> Unit) {
    SectionText(
        text = "Arrangement"
//        stringResource(
//            R.string.arrangement
//        )
    )

    ArrangementOptions(
        listOptionClick = listOptionClick,
        gridOptionClick = gridOptionClick,
    )
}

@Composable
private fun SortingSection(sortingSelected: (io.writeopia.sdk.persistence.core.sorting.OrderBy) -> Unit) {
    SectionText(text = "Sorting"
//    stringResource(R.string.sorting)
    )
    val optionStyle = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.Bold
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = INNER_PADDING.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            modifier = Modifier
                .clickable { sortingSelected(io.writeopia.sdk.persistence.core.sorting.OrderBy.UPDATE) }
                .sortingOptionModifier(),
            text = "Last updated",
//            stringResource(R.string.last_updated)
            style = optionStyle,
        )

        Divider(color = MaterialTheme.colorScheme.primary)

        Text(
            modifier = Modifier
                .clickable { sortingSelected(io.writeopia.sdk.persistence.core.sorting.OrderBy.CREATE) }
                .sortingOptionModifier(),
            text = "Create",
//            stringResource(R.string.last_created),
            style = optionStyle,
        )

        Divider(color = MaterialTheme.colorScheme.primary)

        Text(
            modifier = Modifier
                .clickable { sortingSelected(io.writeopia.sdk.persistence.core.sorting.OrderBy.NAME) }
                .sortingOptionModifier(),
            text = "Name",
//            stringResource(R.string.name),
            style = optionStyle,
        )
    }
}

private fun Modifier.sortingOptionModifier(): Modifier = fillMaxWidth().padding(12.dp)

private fun Modifier.orderConfigModifier(clickable: () -> Unit): Modifier =
    composed {
        clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable(onClick = clickable)
            .padding(6.dp)
    }

//@Preview
//@Composable
//private fun ConfigurationsMenu_Preview() {
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .background(Color.White)) {
//        ConfigurationsMenu(Modifier, true, {}, {}, {}, {})
//    }
//}
//
//@Preview
//@Composable
//private fun ArrangementOptions_Preview() {
//    ArrangementOptions({}, {})
//}
