package com.example.doggy.screens.home.breedlist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.doggy.TopBar
import com.example.doggy.network.DogInfo
import com.example.doggy.ui.theme.myColour2
import kotlinx.coroutines.launch

@Composable
fun BreedListComponent(allDog: List<DogInfo>, onDogClick: (DogInfo) -> Unit) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
        ) {
            items(allDog) { newDog ->
                BreedItemComponent(newDog, onDogClick)
                Log.d("HomePage", newDog.toString())
            }
        } // column end
        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier
                .align(BottomEnd)
                .padding(10.dp),
            shape = CircleShape,
            backgroundColor = myColour2,
            contentColor = Color.White
        ) {
            Icon(Icons.Default.KeyboardArrowUp, "Jump To Top")
        }
    } // Box end
}
