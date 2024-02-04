package com.jr.composedemo

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt


@Composable
fun CircularList(
    modifier: Modifier = Modifier,
    visibleItems: Int,
    state: CircularListState = rememberCircularListState(),
    circularFraction: Float = 1f,
    overshootItems: Int = 3,
    content: @Composable () -> Unit
) {
    check(visibleItems > 0) { "Visible items must be positive" }
    check(circularFraction > 0f) { "Circular fraction must be positive" }

    Layout(
        modifier = modifier
            .clipToBounds()
            .drag(state),
        content = content,
    ) { measurables, constraints ->
        val itemHeight = constraints.maxHeight / visibleItems
        val itemConstraints = Constraints.fixed(width = constraints.maxWidth, height = itemHeight)

        val placeables = measurables.map { measurable -> measurable.measure(itemConstraints) }

        state.setup(
            CircularListConfig(
                contentHeight = constraints.maxHeight.toFloat(),
                numItems = placeables.size,
                visibleItems = visibleItems,
                circularFraction = circularFraction,
                overshootItems = overshootItems
            )
        )

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
//            val verticalOffset = (constraints.maxHeight - itemHeight) / 2
//            val offsetY = verticalOffset + i * itemHeight

            for (i in state.firstVisibleItem..state.lastVisibleItem) {
                placeables[i].placeRelative(state.offsetFor(i))
            }
        }
    }
}

data class CircularListConfig(
    val contentHeight: Float = 0f,
    val numItems: Int = 0,
    val visibleItems: Int = 0,
    val circularFraction: Float = 1f,
    val overshootItems: Int = 0,
)

@Stable
interface CircularListState {
    val verticalOffset: Float
    val firstVisibleItem: Int
    val lastVisibleItem: Int

    suspend fun snapTo(value: Float)
    suspend fun decayTo(velocity: Float, value: Float)
    suspend fun stop()
    fun offsetFor(index: Int): IntOffset
    fun setup(config: CircularListConfig)
}

class CircularListStateImpl(
    currentOffset: Float = 0f,
) : CircularListState {

    private val animatable = Animatable(currentOffset)
    private var itemHeight = 0f
    private var config = CircularListConfig()
    private var initialOffset = 0f

    private val decayAnimationSpec = FloatSpringSpec(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    )

    private val minOffset: Float
        get() = -(config.numItems - 1) * itemHeight

    override val verticalOffset: Float
        get() = animatable.value

    override val firstVisibleItem: Int
        get() = ((-verticalOffset - initialOffset) / itemHeight).toInt().coerceAtLeast(0)

    override val lastVisibleItem: Int
        get() = (((-verticalOffset - initialOffset) / itemHeight).toInt() + config.visibleItems)
            .coerceAtMost(config.numItems - 1)

    override suspend fun snapTo(value: Float) {
        val minOvershoot = -(config.numItems - 1 + config.overshootItems) * itemHeight
        val maxOvershoot = config.overshootItems * itemHeight
        animatable.snapTo(value.coerceIn(minOvershoot, maxOvershoot))

//        animatable.snapTo(value.coerceIn(-(config.numItems - 1) * itemHeight, 0f))
    }

    override suspend fun decayTo(velocity: Float, value: Float) {
        val constrainedValue = value.coerceIn(minOffset, 0f).absoluteValue
        val remainder = (constrainedValue / itemHeight) - (constrainedValue / itemHeight).toInt()
        val extra = if (remainder <= 0.5f) 0 else 1
        val target = ((constrainedValue / itemHeight).toInt() + extra) * itemHeight
        animatable.animateTo(
            targetValue = -target,
            initialVelocity = velocity,
            animationSpec = decayAnimationSpec,
        )
    }

    override suspend fun stop() {
        animatable.stop()
    }

    override fun setup(config: CircularListConfig) {
        this.config = config
        itemHeight = config.contentHeight / config.visibleItems
        initialOffset = (config.contentHeight - itemHeight) / 2f
    }

    override fun offsetFor(index: Int): IntOffset {
        val maxOffset = config.contentHeight / 2f + itemHeight / 2f
        val y = (verticalOffset + initialOffset + index * itemHeight)
        val deltaFromCenter = (y - initialOffset)
        val radius = config.contentHeight / 2f
        val scaledY = deltaFromCenter.absoluteValue * (config.contentHeight / 2f / maxOffset)
        val x = if (scaledY < radius) {
            sqrt((radius * radius - scaledY * scaledY))
        } else {
            0f
        }
        return IntOffset(
            x = (x * config.circularFraction).roundToInt(),
            y = y.roundToInt()
        )
    }

    companion object {
        val Saver = Saver<CircularListStateImpl, List<Any>>(
            save = { listOf(it.verticalOffset) },
            restore = {
                CircularListStateImpl(it[0] as Float)
            }
        )
    }
}

@Composable
fun rememberCircularListState(): CircularListState {
    val state = rememberSaveable(saver = CircularListStateImpl.Saver) {
        CircularListStateImpl()
    }
    return state
}

private fun Modifier.drag(
    state: CircularListState,
) = pointerInput(Unit) {
    val decay = splineBasedDecay<Float>(this)

    coroutineScope {
        // 2
        while (true) {
            // 3
//            val pointerId =  awaitPointerEventScope { awaitFirstDown().id }
            // 4
            state.stop()
            val tracker = VelocityTracker()

            // 5
            awaitPointerEventScope {
                val pointerId = awaitFirstDown().id

                // 6
                verticalDrag(pointerId) { change ->
                    // 7
                    val verticalDragOffset = state.verticalOffset + change.positionChange().y
                    // 8
                    launch {
                        state.snapTo(verticalDragOffset)
                    }
                    tracker.addPosition(change.uptimeMillis, change.position)
                    change.consume()

                    // 9
//                    change.consumePositionChange()
//                    change.consume()
                }
            }

            val velocity = tracker.calculateVelocity().y
            // 5
            val targetValue = decay.calculateTargetValue(state.verticalOffset, velocity)
            launch {
                // 6
                state.decayTo(velocity, targetValue)
            }
        }
    }
}
