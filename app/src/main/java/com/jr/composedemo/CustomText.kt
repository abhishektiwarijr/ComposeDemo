package com.jr.composedemo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CustomText(y: Dp) {
    Layout(content = { Text(text = "Lorem Ipsum") }) { measurables, constraints ->
        val text = measurables[0].measure(constraints)
        layout(constraints.maxWidth, constraints.maxHeight) { //Change these per your needs
            text.placeRelative(IntOffset(0, y.value.roundToInt() - text.height))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCustomText() {
    CustomText(30.dp)
}