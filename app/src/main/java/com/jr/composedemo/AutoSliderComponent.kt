package com.jr.composedemo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSliderComponent(images: List<String>, pagerState: PagerState) {

//    LaunchedEffect(pagerState.currentPage) {
//        delay(3000.milliseconds)
//        scope.launch {
//            val nextPage = ((pagerState.currentPage + 1) % images.size)
//            pagerState.animateScrollToPage(nextPage)
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
                    .height(200.dp)
            ) {
                Image(
                    painter = painter,
                    contentScale = ContentScale.Crop,
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