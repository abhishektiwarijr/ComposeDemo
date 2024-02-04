package com.jr.composedemo.horizontal

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


@Composable
fun ArcList(
    modifier: Modifier = Modifier,
    visibleItems: Int,
    state : ArcListState = rememberArcListState(),
    circularFraction: Float = 1f,
    content: @Composable () -> Unit
) {
    check(visibleItems > 0) { "Visible items must be positive" }
    check(circularFraction > 0f) { "Circular fraction must be positive" }

    Layout(
        modifier = modifier.clipToBounds().drag(state),
        content = content
    ) {measurables, constraints ->

        val itemHeight = constraints.maxHeight
        val itemWidth = constraints.maxWidth / visibleItems

        val itemConstraints = Constraints.fixed(width = itemWidth, height = itemHeight)

        val placeables = measurables.map { measurable ->
            measurable.measure(itemConstraints)
        }

        state.setup(
            ArcListConfig(
                contentWidth = constraints.maxWidth.toFloat(),
                numItems = placeables.size,
                visibleItems = visibleItems,
                circularFraction = circularFraction
            )
        )

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
//            val horizontalOffset = (constraints.maxWidth - itemWidth) / 2

            for (i in state.firstVisibleItem..state.lastVisibleItem) {
//                val offsetX = horizontalOffset + i * itemWidth

                placeables[i].placeRelative(state.offsetFor(i))
            }
        }
    }
}

data class ArcListConfig(
    val contentWidth: Float = 0f,
    val numItems: Int = 0,
    val visibleItems: Int = 0,
    val circularFraction: Float = 1f,
    val overshootItems: Int = 0
)


@Stable
interface ArcListState {
    val horizontalOffset: Float
    val firstVisibleItem: Int
    val lastVisibleItem: Int

    suspend fun snapTo(value: Float)
    suspend fun decayTo(velocity: Float, value: Float)
    suspend fun stop()
    fun offsetFor(index: Int): IntOffset
    fun setup(config: ArcListConfig)
}

class ArcListStateImpl(
    currentOffset: Float = 0f
) : ArcListState {
    private val animatable = Animatable(currentOffset)
    private var config = ArcListConfig()
    private var itemWidth = 0f
    private var initialOffset = 0f

    private val decayAnimationSpec = FloatSpringSpec(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    )

    private val minOffset: Float
        get() = -(config.numItems - 1) * itemWidth

    override val horizontalOffset : Float
        get() = animatable.value

    override val firstVisibleItem: Int
        get() = ((-horizontalOffset - initialOffset) / itemWidth).toInt().coerceAtLeast(0)

    override val lastVisibleItem: Int
        get() = (((-horizontalOffset - initialOffset) / itemWidth).toInt() + config.visibleItems)
            .coerceAtMost(config.numItems - 1)

    override suspend fun snapTo(value: Float) {
        val minOvershoot = -(config.numItems - 1 + config.overshootItems) * itemWidth
        val maxOvershoot = config.overshootItems * itemWidth
        animatable.snapTo(value.coerceIn(minOvershoot, maxOvershoot))
    }

    override suspend fun decayTo(velocity: Float, value: Float) {
        val constrainedValue = value.coerceIn(minOffset, 0f).absoluteValue
        val remainder = (constrainedValue / itemWidth) - (constrainedValue / itemWidth).toInt()
        val extra = if (remainder <= 0.5f) 0 else 1
        val target =((constrainedValue / itemWidth).toInt() + extra) * itemWidth
        animatable.animateTo(
            targetValue = -target,
            initialVelocity = velocity,
            animationSpec = decayAnimationSpec,
        )
    }

    override suspend fun stop() {
        animatable.stop()
    }

    override fun setup(config: ArcListConfig) {
        this.config = config
        itemWidth = config.contentWidth / config.visibleItems
        initialOffset = (config.contentWidth - itemWidth) / 2f
    }

    override fun offsetFor(index: Int): IntOffset {

        val x = (horizontalOffset + initialOffset + index * itemWidth)
        return IntOffset(
            x = x.roundToInt(),
            y = 0
        )

//        val start = (index - 1).coerceAtLeast(0)
//        val middle = index.coerceAtMost(1)
//        val end = (index + 1).coerceAtMost(2)

//        val maxOffset = config.contentWidth / 2f + (itemWidth / 2f)
//        val x = (horizontalOffset + initialOffset + (index * itemWidth))
//
//        val deltaFromCenter = (x - initialOffset)
//        val radius = config.contentWidth / 2f
//
//        val scaledX = deltaFromCenter.absoluteValue * (config.contentWidth / 2f / maxOffset)
////        val scaledX = 1
//
//
//        val y = if (scaledX < radius) {
//            sqrt((radius * radius - scaledX * scaledX))
//        } else {
//            0f
//        }
//
//        return IntOffset(
//            // 6
//            x = (x).roundToInt(),
//            y = (y * config.circularFraction).roundToInt()
//        )


//        return IntOffset(
//            x = x.roundToInt(),
//            y = 0
//        )
    }

    companion object {
        val Saver = Saver<ArcListStateImpl, List<Any>>(
            save = { listOf(it.horizontalOffset) },
            restore = {
                ArcListStateImpl(it[0] as Float)
            }
        )
    }
}

@Composable
fun rememberArcListState(): ArcListState {
    val state = rememberSaveable(saver = ArcListStateImpl.Saver) {
        ArcListStateImpl()
    }
    return state
}

private fun Modifier.drag(
    state: ArcListState,
) = pointerInput(Unit) {
    val decay = splineBasedDecay<Float>(this)

    coroutineScope {
        while (true) {
//            val pointerId = awaitPointerEventScope { awaitFirstDown().id }
            state.stop()
//            state.stop()
            val tracker = VelocityTracker()

            awaitPointerEventScope {
                val pointerId = awaitFirstDown().id

                verticalDrag(pointerId) { change ->

                    val horizontalDragOffset = state.horizontalOffset + change.positionChange().x

                    launch {
                        state.snapTo(horizontalDragOffset)
                    }
                    tracker.addPosition(change.uptimeMillis, change.position)

                    change.consume()
                }
            }

            val velocity = tracker.calculateVelocity().x
            val targetValue = decay.calculateTargetValue(state.horizontalOffset, velocity)

            launch {
                state.decayTo(velocity, targetValue)
            }
        }
    }
}
