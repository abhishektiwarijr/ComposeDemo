package com.jr.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jr.composedemo.horizontal.ArcList
import com.jr.composedemo.ui.theme.ComposeDemoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animals = listOf(
            "https://images.unsplash.com/photo-1474511320723-9a56873867b5?q=80&w=2944&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "https://plus.unsplash.com/premium_photo-1675715924047-a9cf6c539d9b?q=80&w=2942&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "https://images.unsplash.com/photo-1546182990-dffeafbe841d?q=80&w=2918&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "https://images.unsplash.com/photo-1437622368342-7a3d73a34c8f?q=80&w=1920&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        )

        setContent {
            ComposeDemoTheme {
//                LazyColumn(
//                    modifier = Modifier
//                        .padding(10.dp)
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                ) {
//                    items(5) {
//                        AutoSliderComponent(animals)
//                        Spacer(modifier = Modifier.height(10.dp))
//                    }
//                }
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    LazyRowWithScrollMotion()
//                }
//                SShapeList()
//                CircularListSample()
//                PullToRefreshInNonScrollableContainer()
//                SShapeList()
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                    ) {
//                        val colors = remember { prepareColorsList() }
//                        val backgroundColor = remember {
//                            mutableStateOf(colors.first().color)
//                        }
//                        val bgColor: Color by animateColorAsState(
//                            backgroundColor.value,
//                            animationSpec = tween(300, easing = LinearEasing),
//                            label = "BackgroundColor"
//                        )
//
//                        var horizontalPadding by remember { mutableStateOf(0.dp) }
//                        val density = LocalDensity.current
//                        val state = rememberLazyListState()
//                        val coroutineScope = rememberCoroutineScope()
//
//                        var selectedItemIndex by remember { mutableIntStateOf(0) }
//                        val centerItemIndex by remember {
//                            derivedStateOf {
//                                state.layoutInfo.visibleItemsInfo.run {
//                                    val firstVisibleItemIndex = state.firstVisibleItemIndex
//                                    if (isEmpty()) -1 else firstVisibleItemIndex + (lastIndex - firstVisibleItemIndex)
//                                }
//                            }
//                        }
//
//                        arrayOf(intArrayOf()).contentDeepToString()
//
//                        val centerPosition by remember {
//                            // caching position for prevent recomposition
//                            derivedStateOf {
//                                val visibleInfo = state.layoutInfo.visibleItemsInfo
//                                if (visibleInfo.isEmpty()) {
//                                    -1
//                                } else {
//                                    val offset =
//                                        (visibleInfo.last().index - visibleInfo.first().index) / 2
//                                    visibleInfo.first().index + offset
//                                }
//                            }
//                        }
//
//                        LazyRow(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(bgColor)
//                                .onGloballyPositioned {
//                                    with(density) {
////                                        horizontalPadding = (it.size.width / 2 - 25).toDp()
//                                        horizontalPadding =
//                                            (it.size.width / 2 - 25.dp.toPx()).toDp()
//                                    }
//                                },
//                            state = state,
//                            flingBehavior = rememberSnapFlingBehavior(
//                                lazyListState = state
//                            ),
//                            horizontalArrangement = Arrangement.spacedBy(50.dp),
//                            contentPadding = PaddingValues(horizontal = horizontalPadding),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            itemsIndexed(colors) { index, color ->
//                                val translation: Offset by animateOffsetAsState(
//                                    targetValue = Offset(0f, color.yOffset.value),
//                                    label = "translationY"
//                                )
//
//                                val scale by animateFloatAsState(
//                                    targetValue = if (index == selectedItemIndex) 1.5f else 1f,
//                                    label = "scale",
//                                    animationSpec = tween(300)
//                                )
//
//                                ColorItemComponent(
//                                    modifier = Modifier
////                                        .fillMaxWidth(0.33f)
//                                        .graphicsLayer {
//                                            scaleX = scale
//                                            scaleY = scale
//                                        }
////                                    .fillParentMaxWidth(0.33f)
////                                        .fillMaxWidth()
//                                    ,
//                                    color,
//                                    translation
//                                ) { selectedColor ->
//                                    backgroundColor.value = selectedColor
//                                    selectedItemIndex = index
//                                    coroutineScope.launch {
//                                        state.animateScrollToItem(selectedItemIndex)
//                                    }
//                                }
//                            }
//                        }
//
//                        Row(
//                            modifier = Modifier.fillMaxSize()
//                        ) {
//                            Spacer(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .fillMaxHeight()
//                            )
//                            Spacer(
//                                modifier = Modifier
//                                    .width(1.dp)
//                                    .fillMaxHeight()
//                                    .background(Color.Black)
//                            )
//                            Spacer(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .fillMaxHeight()
//                            )
//                        }
//                    }
//                }
//                ArcListSample()
//                TestSShapeList()
                val colors = remember { prepareColorsList() }
                val scrollState = rememberLazyListState()

                val itemWidth = 70.dp.dpToPx()
                val totalWidth = colors.size * itemWidth

                val maxScrollOffset = totalWidth - scrollState.layoutInfo.viewportSize.width
                val arcFactor = 0.05 // Adjust this factor to control the curvature of the arc
                val yOffset = remember { Animatable(0f) }

                LaunchedEffect(scrollState) {
                    while (true) {
                        val progress = scrollState.firstVisibleItemScrollOffset.toFloat() / maxScrollOffset
                        val angle = arcFactor * 2 * Math.PI * progress // Use arc function
                        val y = Math.sin(angle) * maxScrollOffset // Apply sinusoidal function
                        yOffset.animateTo(y.toFloat())
                        delay(16) // Update every frame (assuming 60fps)
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    LazyRow(
                        state = scrollState,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        items(colors) { color ->
                            Box(
                                modifier = Modifier
                                    .fillParentMaxWidth(0.33f)
                                    .offset(y= yOffset.value.dp)
                                    .background(Color.Transparent),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Card(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(CircleShape),
                                    shape = CircleShape,
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 2.dp
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .border(5.dp, Color.White, CircleShape)
                                            .background(color.color),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullToRefreshInNonScrollableContainer() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        var isRefreshing by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()


        val pullRefreshState = rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = {
                scope.launch {
                    isRefreshing = true
                    delay(5_000L)
                    isRefreshing = false
                }
            }
        )


        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
                .pullRefresh(state = pullRefreshState)
                .verticalScroll(rememberScrollState())


        ) {
            val (topBox, bottomBox, topIndicator) = createRefs()

            Box(
                modifier = Modifier
                    .height(400.dp)
                    .width(400.dp)
                    .paint(
                        painterResource(id = R.drawable.ic_launcher_background),
                        contentScale = ContentScale.FillBounds
                    )
                    .constrainAs(topBox) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 8.dp)
                    .constrainAs(bottomBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.constrainAs(topIndicator) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Composable
fun ArcListSample() {
    val colors by remember {
        mutableStateOf(
            listOf(
                Color.Red,
                Color.Green,
                Color.Blue,
                Color.Magenta,
                Color.Yellow,
                Color.Cyan,
            )
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ArcList(
            visibleItems = 3,
            circularFraction = 0.65f,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            for (i in 0 until 40) {
                ListItem(
                    text = "Item #$i",
                    color = colors[i % colors.size],
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CircularListSample() {
    val colors by remember {
        mutableStateOf(
            listOf(
                Color.Red,
                Color.Green,
                Color.Blue,
                Color.Magenta,
                Color.Yellow,
                Color.Cyan,
            )
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CircularList(
            visibleItems = 12,
            circularFraction = 0.65f,
            overshootItems = 5,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            for (i in 0 until 40) {
                ListItem(
                    text = "Item #$i",
                    color = colors[i % colors.size],
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun ListItem(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .padding(all = 8.dp)
                .clip(shape = CircleShape)
                .background(color = color)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TestSShapeList() {
    val colors = remember { prepareColorsList() }
    val backgroundColor = remember {
        mutableStateOf(colors.first().color)
    }
    val bgColor: Color by animateColorAsState(
        backgroundColor.value,
        animationSpec = tween(300, easing = LinearEasing),
        label = "BackgroundColor"
    )
    val itemHeight = 50.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val middleItemY = screenHeight / 2 - itemHeight / 2
    val scrollState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            state = scrollState,
            flingBehavior = rememberSnapFlingBehavior(
                lazyListState = scrollState
            ),
//            horizontalArrangement = Arrangement.spacedBy(12.dp),
//            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
        ) {
            itemsIndexed(colors) { index, item ->
//                scrollOffset += (index * scrollState.firstVisibleItemScrollOffset)


                val offsetY = when (index % 3) {
                    0 -> middleItemY + itemHeight
                    1 -> middleItemY
                    else -> middleItemY - itemHeight
                }

                Box(
                    modifier = Modifier
                        .offset(y = offsetY)
//                        .offset(translation.x.dp, translation.y.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(item.color),
                ) {

                    // Item content
                }

//                ColorItemComponent(
//                    modifier = Modifier
////                        .offset(y = offsetY.dp)
//                        .size(width = 50.dp, height = itemHeight.dp),
//                    item,
//                    translation
//                ) { color ->
//                    backgroundColor.value = color
//                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SShapeList(modifier: Modifier = Modifier) {
    val colors = remember { prepareColorsList() }
    val backgroundColor = remember {
        mutableStateOf(colors.first().color)
    }
    val bgColor: Color by animateColorAsState(
        backgroundColor.value,
        animationSpec = tween(300, easing = LinearEasing),
        label = "BackgroundColor"
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
//        val scrollState = rememberLazyListState()
//        var scrollOffset by remember { mutableStateOf(0f) }
//        val snappingLayout = remember(scrollState) { SnapLayoutInfoProvider(scrollState) }
//        val flingBehavior = rememberSnapFlingBehavior(snappingLayout)

//        val pagerState = rememberPagerState(
//            initialPage = 0,
//            initialPageOffsetFraction = 0f
//        ) {
//            // provide pageCount
//        }

        val pagerState = rememberPagerState(pageCount = {
            colors.size
        })

//        val fullyVisibleIndices: List<Int> by remember {
//            derivedStateOf {
//                val layoutInfo = scrollState.layoutInfo
//                val visibleItemsInfo = layoutInfo.visibleItemsInfo
//                if (visibleItemsInfo.isEmpty()) {
//                    emptyList()
//                } else {
//                    val fullyVisibleItemsInfo = visibleItemsInfo.toMutableList()
//
//                    val lastItem = fullyVisibleItemsInfo.last()
//
//                    val viewportHeight =
//                        layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
//
//                    if (lastItem.offset + lastItem.size > viewportHeight) {
//                        fullyVisibleItemsInfo.removeLast()
//                    }
//
//                    val firstItemIfLeft = fullyVisibleItemsInfo.firstOrNull()
//                    if (firstItemIfLeft != null && firstItemIfLeft.offset < layoutInfo.viewportStartOffset) {
//                        fullyVisibleItemsInfo.removeFirst()
//                    }
//
//                    fullyVisibleItemsInfo.map { it.index }
//                }
//            }
//        }
        val coroutineScope = rememberCoroutineScope()
//        pagerState.currentPage
//        var visibleIndices by remember { mutableStateOf(emptyList<Int>()) }
//
//        val translation: Dp by animateDpAsState(
//                when (page) {
//                    first -> (200 - 50).dp
//                    second -> (100 - 25).dp
//                    else -> 0.dp
//                },
//                label = "translationY"
//            )
//        val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

        LaunchedEffect(pagerState.currentPage) {
            coroutineScope.launch {
                val startIndex = pagerState.currentPage
                val endIndex = (startIndex + 3 - 1).coerceAtMost(colors.size - 1)
                val centerIndex = (startIndex + endIndex) / 2

                colors.apply {
                    this[startIndex].yOffset = (200 - 50).dp
                    this[centerIndex].yOffset = (100 - 25).dp
                    this[endIndex].yOffset = 0.dp
                }
            }
        }

        HorizontalPager(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, _, _ ->
                        coroutineScope.launch {
                            // Handle horizontal swipe gestures
//                            pagerState.scrollBy(pan.x)
                        }
                    }
                },
            state = pagerState,
            pageSize = object : PageSize {
                override fun Density.calculateMainAxisPageSize(
                    availableSpace: Int,
                    pageSpacing: Int
                ): Int {
                    return ((availableSpace - 2 * pageSpacing) / 3)
                }
            }
        ) { pageIndex ->
            val pageOffset =
                ((pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction).absoluteValue

            val translation: Offset by animateOffsetAsState(
                targetValue = Offset(pageOffset, colors[pageIndex].yOffset.value),
                label = "translationY"
            )
            ColorItemComponent(
                modifier = Modifier
                    .fillMaxSize(),
                colors[pageIndex],
                translation
            ) { color ->
                backgroundColor.value = color
            }
        }

        // scroll to page

        Button(onClick = {
            coroutineScope.launch {
                // Call scroll to on pagerState
                pagerState.animateScrollToPage(
                    pagerState.currentPage + 1
                )
            }
        }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text("Jump to Next Page")
        }


//        LazyRow(
//            modifier = modifier
//                .fillMaxWidth()
//                .height(200.dp)
////                .pointerInput(Unit) {
////                    detectTransformGestures { centroid, pan, zoom, rotation ->
////                        scrollOffset += pan.x
////                    }
////                }
//                .background(Color.Black),
//            state = scrollState,
//            flingBehavior = flingBehavior,
////            horizontalArrangement = Arrangement.spacedBy(12.dp),
////            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
//        ) {
//            itemsIndexed(colors) { index, item ->
////                scrollOffset += (index * scrollState.firstVisibleItemScrollOffset)
//
//                var first = 0
//                var second = 1
//
//                if (fullyVisibleIndices.isNotEmpty()) {
//                    first = fullyVisibleIndices[0]
//                    second = fullyVisibleIndices[1]
//                }
//
//                val targetValue = when (index) {
//                    first -> (200 - 50).dp
//                    second -> (100 - 25).dp
//                    else -> 0.dp
//                }
//
//                val offsetY = targetValue
//
//                ColorItemComponent(
//                    modifier = Modifier
//                        .fillParentMaxWidth(0.33f)
//                        .fillParentMaxHeight(0.33f),
//                    item,
//                    offsetY
//                ) { color ->
//                    backgroundColor.value = color
//                }
//            }
//        }
    }
}

// Calculate Y offset for arc-shaped layout
@Composable
private fun calculateYOffset(itemOffset: Int, scrollOffset: Int): Int {
//    val screenWidth = LocalConfiguration.current.screenWidthDp.dp.dpToPx()
    val amplitude = 200 - 55 // The maximum height of the arc
    val frequency = 0.5 // Controls how tightly the arc bends (lower values create a tighter bend)

    // Calculate the Y offset based on the scroll offset and item position
    val yOffset = (amplitude * Math.cos(frequency * (itemOffset - scrollOffset))).toInt()
    return yOffset
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.roundToPx() }

@Composable
fun ColorItemComponent(
    modifier: Modifier = Modifier,
    colorItem: ColorItem,
    offset: Offset,
    onItemClick: (color: Color) -> Unit
) {
    Box(
        modifier = modifier
//            .offset(offset.x.dp, offset.y.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .clickable {
                    onItemClick(colorItem.color)
                },
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(5.dp, Color.White, CircleShape)
                    .background(colorItem.color),
            )
        }
    }
}

fun String.fromHex() = Color(android.graphics.Color.parseColor(this))


private fun prepareColorsList(): MutableList<ColorItem> {
    return mutableListOf<ColorItem>(
        ColorItem("#F44336".fromHex(), (200 - 50).dp),
        ColorItem("#E91E63".fromHex(), (100 - 25).dp),
        ColorItem("#9C27B0".fromHex(), 0.dp),
        ColorItem("#673AB7".fromHex(), 0.dp),
        ColorItem("#3F51B5".fromHex(), 0.dp),
        ColorItem("#2196F3".fromHex(), 0.dp),
        ColorItem("#03A9F4".fromHex(), 0.dp),
        ColorItem("#00BCD4".fromHex(), 0.dp),
        ColorItem("#009688".fromHex(), 0.dp),
        ColorItem("#4CAF50".fromHex(), 0.dp),
        ColorItem("#8BC34A".fromHex(), 0.dp),
        ColorItem("#CDDC39".fromHex(), 0.dp),
        ColorItem("#FFEB3B".fromHex(), 0.dp),
        ColorItem("#FFC107".fromHex(), 0.dp),
        ColorItem("#FF9800".fromHex(), 0.dp),
        ColorItem("#FF5722".fromHex(), 0.dp),
        ColorItem("#607D8B".fromHex(), 0.dp),
        ColorItem("#795548".fromHex(), 0.dp),
    )
}

data class ColorItem(val color: Color, var yOffset: Dp)

@Composable
fun FullWidthCard(modifier: Modifier = Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)

    ) {
        Box {

            Image(
                painter = painterResource(R.drawable.ab1_inversions),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            PillBadgeBox("")
        }
    }
}

@Composable
fun PillBadgeBox(message: String) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .background(color = Color.Black, shape = RoundedCornerShape(3.dp, 0.dp, 0.dp, 3.dp))
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(8.dp, 4.dp),
            style = TextStyle(
                fontSize = 10.sp,
                lineHeight = 14.sp,
                color = Color.White
            )
        )
        Box(
            modifier = Modifier
                .padding(1.dp)
                .width(12.dp)
                .wrapContentHeight()
                .background(color = Color.Black)
        )
    }
}

@Composable
fun MessageBox(message: String) {
    val cornerShape = with(LocalDensity.current) { 8.dp.toPx() }
    val arrowWidth = with(LocalDensity.current) { 12.dp.toPx() }
    val arrowHeight = with(LocalDensity.current) { 12.dp.toPx() }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .drawRightBubble(
                cornerShape = cornerShape,
                arrowWidth = arrowWidth,
                arrowHeight = arrowHeight,
                bubbleColor = Color.Green
            )
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(8.dp, 4.dp),
            fontSize = 14.sp,
            color = Color.Black
        )
    }

}

@Composable
fun TicketComposable(modifier: Modifier) {
    Text(
        text = "🎉 CINEMA TICKET 🎉",
        style = TextStyle(
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
        ),
        textAlign = TextAlign.Center,
        modifier = modifier
            .wrapContentSize()
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                shape = TicketShape(24.dp.toPx())
                clip = true
            }
            .background(color = MaterialTheme.colorScheme.background)
            .drawBehind {
                scale(scale = 0.9f) {
                    drawPath(
                        path = drawTicketPath(size = size, cornerRadius = 24.dp.toPx()),
                        color = Color.Cyan,
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                    )
                }
            }
            .padding(start = 32.dp, top = 64.dp, end = 32.dp, bottom = 64.dp)
    )
}


@Composable
fun AlignYourBodyElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(text),
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeDemoTheme {
        Greeting("Android")
    }
}