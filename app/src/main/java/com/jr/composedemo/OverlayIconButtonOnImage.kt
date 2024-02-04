package com.jr.composedemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OverlayIconButtonOnImage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
//        val density = LocalDensity.current.density
        Box(
            modifier = Modifier.layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                val margin = (16 * density).toInt()
                layout(placeable.width + margin, placeable.height + margin) {
                    placeable.placeRelative(margin, margin)
                }
            }
        ) {
            IconButton(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOverlayIconButtonOnImage() {
    OverlayIconButtonOnImage()
}