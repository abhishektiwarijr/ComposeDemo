package com.jr.composedemo

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//@Composable
//fun NeonButton(
//    text: String,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    contentColor: Color = Color.Red,
//    backgroundColor: Color = Color.Transparent,
//) {
//    Box(
//        modifier = modifier
//            .background(backgroundColor)
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .clickable(onClick = onClick)
//            .shadow(
//                color = Color.White.withOpacity(0.3f),
//                blurRadius = 4.dp,
//                offset = Offset(x = 0f, y = 2.dp)
//            )
//    ) {
//        Text(
//            text = text,
//            color = contentColor,
//            fontSize = 24.sp,
//            letterSpacing = 4.sp,
//            modifier = Modifier
//                .background(
//                    Brush.horizontalGradient(
//                        colors = listOf(
//                            Color.Red.copy(alpha = 0.7f),
//                            Color.Magenta.copy(alpha = 0.4f),
//                        )
//                    )
//                )
//                .padding(horizontal = 4.dp)
//        )
//    }
//}


@Composable
private fun NeonSample1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val paint = remember {
            Paint().apply {
                style = PaintingStyle.Stroke
                strokeWidth = 30f
            }
        }

        val frameworkPaint = remember {
            paint.asFrameworkPaint()
        }

        val color = Color.Red

        val transparent = color
            .copy(alpha = 0f)
            .toArgb()

        frameworkPaint.color = transparent

        frameworkPaint.setShadowLayer(
            10f,
            0f,
            0f,
            color
                .copy(alpha = .5f)
                .toArgb()
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            inset(10.dp.toPx()){
                this.drawIntoCanvas {
                    it.drawRoundRect(
                        left = 0f,
                        top = 0f,
                        right = size.width,
                        bottom = size.height,
                        radiusX = 5.dp.toPx(),
                        5.dp.toPx(),
                        paint = paint
                    )

                    drawRoundRect(
                        Color.White,
                        cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            }
        }
    }
}


@Composable
private fun NeonSample2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val paint = remember {
            Paint().apply {
                style = PaintingStyle.Stroke
                strokeWidth = 30f
            }
        }

        val frameworkPaint = remember {
            paint.asFrameworkPaint()
        }

        val color = Color.Red


        Canvas(modifier = Modifier.fillMaxSize()) {
            this.drawIntoCanvas {

                val transparent = color
                    .copy(alpha = 0f)
                    .toArgb()

                frameworkPaint.color = transparent

                frameworkPaint.setShadowLayer(
                    10f,
                    0f,
                    0f,
                    color
                        .copy(alpha = .5f)
                        .toArgb()
                )

                it.drawRoundRect(
                    left = 100f,
                    top = 100f,
                    right = 500f,
                    bottom = 500f,
                    radiusX = 5.dp.toPx(),
                    5.dp.toPx(),
                    paint = paint
                )

                drawRoundRect(
                    Color.White,
                    topLeft = Offset(100f, 100f),
                    size = Size(400f, 400f),
                    cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )


                frameworkPaint.setShadowLayer(
                    30f,
                    0f,
                    0f,
                    color
                        .copy(alpha = .5f)
                        .toArgb()
                )


                it.drawRoundRect(
                    left = 600f,
                    top = 100f,
                    right = 1000f,
                    bottom = 500f,
                    radiusX = 5.dp.toPx(),
                    5.dp.toPx(),
                    paint = paint
                )

                drawRoundRect(
                    Color.White,
                    topLeft = Offset(600f, 100f),
                    size = Size(400f, 400f),
                    cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}


fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 1f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}



@Preview
@Composable
fun PreviewNeonSample() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//    ) {
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .advancedShadow(
//                    color =  Color.Red,
//                    alpha = 0.5f,
//                    cornersRadius = 8.dp,
//                    shadowBlurRadius = 5.dp,
//                    offsetX = 10.dp,
//                    offsetY = 10.dp
//                )
//        )
//    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        NeonSample1()
        NeonSample2()
    }

}

@Composable
fun ShadowUsageSample() {
    val height by remember { mutableStateOf(80f) }
    val radius by remember { mutableStateOf(22f) }
    val shadowBorderRadius by remember { mutableStateOf(22f) }
    val offsetX by remember { mutableStateOf(7f) }
    val offsetY by remember { mutableStateOf(7f) }
    val spread by remember { mutableStateOf(7f) }
    val blurRadius by remember { mutableStateOf(22f) }
    val shadowColor by remember { mutableStateOf(Color.Black) }

    Box(
        modifier = Modifier
            .padding(40.dp)
            .shadow(
                shadowColor,
                borderRadius = shadowBorderRadius.dp,
                offsetX = offsetX.dp,
                offsetY = offsetY.dp,
                spread = spread.dp,
                blurRadius = blurRadius.dp
            )
            .fillMaxWidth()
            .height(height.dp)
            .clip(RoundedCornerShape(radius.dp))
            .background(Color.White)
    ) {

    }
}

fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)

