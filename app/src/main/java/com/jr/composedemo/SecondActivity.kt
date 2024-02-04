package com.jr.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.jr.composedemo.ui.theme.ComposeDemoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.milliseconds

class SecondActivity : ComponentActivity() {
    private val imageLoader by lazy {
        ImageLoader.Builder(applicationContext)
            .allowRgb565(true)
            .build()
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            CompositionLocalProvider(LocalImageLoader provides imageLoader) {
//                MainScreen(data = items)
//            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(HexToJetpackColor.getColor("242935"))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    items(5) {
                        val images = remember { imageUrls }

                        val pagerState = rememberPagerState(pageCount = {
                            images.size
                        })
                        val scope = rememberCoroutineScope()
                        val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

                        LaunchedEffect(pagerState.currentPage) {
                            delay(3000.milliseconds)
                            scope.launch {
                                val nextPage = ((pagerState.currentPage + 1) % images.size)
                                pagerState.animateScrollToPage(nextPage)
                            }
                        }

                        OverlappingBoxes(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    HorizontalPager(
                                        state = pagerState,
                                        key = { images[it] },
                                        pageSize = PageSize.Fill
                                    ) { index ->
                                        val painter = rememberAsyncImagePainter(images[index])
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .heightIn(max = 200.dp)
                                        ) {
                                            Image(
                                                painter = painter,
                                                contentScale = ContentScale.FillBounds,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                            )
                                            if (painter.state is AsyncImagePainter.State.Loading) {
                                                CircularProgressIndicator(Modifier.align(Alignment.Center))
                                            }
                                        }
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
//                                .background(Color(0f, 0f, 0f, 0.5f)),
                            ) {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                pagerState.currentPage - 1
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .offset(x = (-8).dp)
                                        .clip(CircleShape)
                                        .size(40.dp)
                                        .align(Alignment.CenterVertically)
                                        .background(Color.White, CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowLeft,
                                        contentDescription = "Go back"
                                    )
                                }

                                DotsIndicator(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                        .weight(1f)
                                        .align(Alignment.Bottom),
                                    totalDots = images.size,
                                    selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                                    dotSize = 8.dp
                                )

                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                pagerState.currentPage + 1
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .offset(x = 8.dp)
                                        .clip(CircleShape)
                                        .size(40.dp)
                                        .align(Alignment.CenterVertically)
                                        .background(Color.White, CircleShape)

                                ) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowRight,
                                        contentDescription = "Go forward"
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }

    companion object {
        private val imageUrls = listOf(
            "https://picsum.photos/id/1025/4951/3301",
            "https://picsum.photos/id/1012/3973/2639",
            "https://picsum.photos/id/102/4320/3240",
//            "https://picsum.photos/id/1004/5616/3744",
//            "https://picsum.photos/id/1011/5472/3648",
//            "https://picsum.photos/id/1019/5472/3648",
            "https://picsum.photos/id/187/4000/2667",
            "https://picsum.photos/id/1020/4288/2848",
            "https://picsum.photos/id/1021/2048/1206",
            "https://picsum.photos/id/1024/1920/1280",
            "https://picsum.photos/id/1013/4256/2832"
        )
        private val items = imageUrls.mapIndexed { i, s -> ImageItem(i, "Title $i", s) }
    }
}

@Preview("ImageCard")
@Composable
fun ImageCardPreview() {
    ComposeDemoTheme {
        ImageCard(item = ImageItem(0, "Sample", ""), modifier = Modifier.height(250.dp))
    }
}

@Preview("MainScreen", showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeDemoTheme {
        MainScreen(data = List(5) { ImageItem(it, "Sample $it", "") })
    }
}


@Composable
private fun MainScreen(data: List<ImageItem>) {
    val state = rememberLazyListState()
    ComposeDemoTheme {
        LazyColumn(
            state = state,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            item { ScaleFadeImageCardRow(data) }
            items(2) { ImageCardRow(data) }
            items(data, { it.id }) { MaxWidthImageCard(state, it) }
            items(2) { ImageCardRow(data) }
        }
    }
}

@Composable
private fun ImageCardRow(items: List<ImageItem>) {
    val state: LazyListState = rememberLazyListState()
    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        items(items, key = { it.id }) { item ->
            ImageCard(
                item,
                modifier = Modifier.size(160.dp, 140.dp),
                imageModifier = Modifier
                    .requiredWidth(220.dp)
                    .graphicsLayer {
                        translationX = state.layoutInfo.normalizedItemPosition(item.id) * -30
                    }
            )
        }
    }
}

@Composable
private fun MaxWidthImageCard(state: LazyListState, item: ImageItem) {
    ImageCard(
        item,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(start = 16.dp, end = 16.dp),
        imageModifier = Modifier
            .requiredHeight(350.dp)
            .graphicsLayer {
                translationY = state.layoutInfo.normalizedItemPosition(item.id) * -50
            },
    )
}

@Composable
private fun ScaleFadeImageCardRow(items: List<ImageItem>) {
    val state: LazyListState = rememberLazyListState()
    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        items(items, key = { it.id }) { item ->
            ImageCard(
                item,
                modifier = Modifier
                    .size(160.dp, 140.dp)
                    .graphicsLayer {
                        val value =
                            1 - (state.layoutInfo.normalizedItemPosition(item.id).absoluteValue * 0.15F)

                        alpha = value
                        scaleX = value
                        scaleY = value
                    },
                imageModifier = Modifier.requiredWidth(220.dp)
            )
        }
    }
}

@Composable
private fun ImageCard(
    item: ImageItem,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        val painter = rememberAsyncImagePainter(item.imageUrl)
        Box {
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = item.title,
                modifier = imageModifier
            )
            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            Text(
                text = item.title,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(0f to Color.Transparent, 1f to Color.Black))
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 32.dp),
                color = Color.White,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}