package com.jr.composedemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircularProfileWithEditButton() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .padding(8.dp) // Add padding to create space for the edit button
            .background(Color.Black)
    ) {
        // Profile Picture
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            // Image content here
        }

        // Edit Button
        Box(
            modifier = Modifier
                .offset(x = (-8).dp, y = (-8).dp)
                .background(Color.Yellow)
                // Add padding inside the edit button to create space for its content
                 // Use offset to move the edit button outside the bounds
        ) {
            Row(modifier = Modifier.fillMaxWidth().height(40.dp)) {

            }
            // Edit button content (e.g., Icon)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewCircularProfileWithEditButton() {
    CircularProfileWithEditButton()
}
