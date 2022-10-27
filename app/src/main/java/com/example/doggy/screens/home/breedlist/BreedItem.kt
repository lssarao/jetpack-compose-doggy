package com.example.doggy.screens.home.breedlist

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.doggy.network.DogInfo

private const val TAG = "BreedList"

@Composable
fun BreedItemComponent(dogData: DogInfo, onDogClick: (DogInfo) -> Unit) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(dogData.image.url)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Fit
    )
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.absolutePadding(left = 16.dp, right = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Log.d(TAG, "BreedItem: BoxClick")
                    onDogClick(dogData)
                }
        ) {
            Row {
                Image(
                    painter = painter,
                    contentDescription = "Dog Image",
                    modifier = Modifier.size(150.dp),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(
                        text = dogData.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.absolutePadding(top = 8.dp, left = 12.dp, right = 12.dp)
                    )
                    Text(
                        text = dogData.temperament,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.absolutePadding(top = 8.dp, left = 12.dp, right = 12.dp)
                    )
                } //2 Column end
            } // Row ending
        } // Card ending
    } // 1 Column end
}
