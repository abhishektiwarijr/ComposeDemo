package com.jr.composedemo

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ArcLazyRow(
    items: List<ColorItem>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (item: ColorItem) -> Unit
) {

    // Calculate total width of all items
    val itemWidth = 55.dp.dpToPx()
    val totalWidth = items.size * itemWidth

    // Get LazyRow's scroll state
    val scrollState = rememberLazyListState()

    // Calculate scroll offset based on arc function
    val maxScrollOffset = totalWidth - scrollState.layoutInfo.viewportSize.width
    val arcFactor = 0.1 // Adjust this factor to control the curvature of the arc
    val xOffset = remember { Animatable(0f) }

    // Apply the arc function to calculate Y offset
    val yOffset = rememberUpdatedState(0f)
    LaunchedEffect(scrollState) {
        while (true) {
            val progress = scrollState.firstVisibleItemScrollOffset.toFloat() / maxScrollOffset
            val angle = arcFactor * 2 * Math.PI * progress // Use arc function
            val x = Math.sin(angle) * maxScrollOffset // Apply sinusoidal function
            xOffset.animateTo(x.toFloat())
            delay(16) // Update every frame (assuming 60fps)
        }
    }

    Box(
        modifier = modifier
            .offset(x = xOffset.value.dp)
    ) {
        LazyRow(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
        ) {
            items(items.size) { index ->
                itemContent(items[index])
            }
        }
    }
}
