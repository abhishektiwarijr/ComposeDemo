package com.jr.composedemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun OverlappingBoxes(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val largeBox = measurables[0]
        val smallBox = measurables[1]
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val largePlaceable = largeBox.measure(looseConstraints)
        val smallPlaceable = smallBox.measure(looseConstraints)
//        val yOffsetOfOverlappingItem = smallPlaceable.height / 2
        layout(
            width = constraints.maxWidth,
            height = largePlaceable.height + 16.dp.toPx().roundToInt(),
        ) {
            largePlaceable.placeRelative(
                x = 0,
                y = 0,
            )
            smallPlaceable.placeRelative(
                x = 0,
                y = largePlaceable.height - 24.dp.toPx().roundToInt()
            )
        }
    }
}
