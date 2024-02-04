package com.jr.composedemo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest

@Composable
fun CoverAndProfileImage(
    modifier: Modifier = Modifier,
    coverImage: Any?,
    profileImage: Any?,
    onCoverClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
) {
    OverlappingBoxes(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { onCoverClick() }
        ) {
            ImageItemUI(
                modifier = Modifier.fillMaxWidth(),
                data = coverImage ?: R.drawable.ic_launcher_background,
                contentScale = ContentScale.FillWidth
            )
        }

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onProfileClick() }
        ) {
            ImageItemUI(
                modifier = Modifier.fillMaxWidth(),
                data = profileImage ?: R.drawable.ic_launcher_background,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ImageItemUI(
    modifier: Modifier,
    data: Any?,
    crossfadeValue: Int = 300,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    coil.compose.AsyncImage(
        modifier = modifier.height(100.dp),
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(crossfadeValue)
            .build(),
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        contentScale = contentScale
    )
}

@Preview
@Composable
fun CoverAndProfileImagePreview() {
    CoverAndProfileImage(
        coverImage = "https://9to5google.com/wp-content/uploads/sites/4/2021/02/android-jetpack-header.png",
        profileImage = "https://3.bp.blogspot.com/-VVp3WvJvl84/X0Vu6EjYqDI/AAAAAAAAPjU/ZOMKiUlgfg8ok8DY8Hc-ocOvGdB0z86AgCLcBGAsYHQ/s1600/jetpack%2Bcompose%2Bicon_RGB.png"
    )
}

