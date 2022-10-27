package com.example.doggy.screens.home.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.doggy.ui.theme.myColour1
import com.example.doggy.ui.theme.myColour2

@Composable
fun WelcomeComponent() {
    val url =
        "https://img.freepik.com/premium-vector/cute-brown-dog-happy-greet-cartoon-illustration_511562-3.jpg"
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build()
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Image(
                painter = painter,
                contentDescription = "Doggy",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(190.dp)
                    .padding(20.dp)
                    .clip(CircleShape)
            )
            CircularProgressIndicator(
                color = myColour2,
                modifier = Modifier.padding(top = 80.dp)
            )
        } // 1 Column end
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.padding(top = 700.dp)
        ) {
            Text(
                text = "Crafted with love in Punjab",
                color = myColour1
            )
        } // 2 Column end
    } // Box end
}