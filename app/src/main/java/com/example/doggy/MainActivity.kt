package com.example.doggy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.doggy.ui.theme.DoggyTheme

/**
 * API
 * - input: null
 * - output: list of dogs
 *
 * Homepage
 * - input: list of images
 * - output: Shows list of breeds
 *
 * Dog details
 * - when clicked on dog image
 * - input: dog
 * - output: dog information
 */
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoggyTheme {
                var topbarState by remember { mutableStateOf(false) }

                navController = rememberNavController()

                Scaffold(
                    topBar = {
                        if (topbarState) {
                            TopAppBar(
                                title = { Text(text = "Dog Breeds") },
                                actions = {
                                    IconButton(onClick = {
                                    }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Search,
                                            contentDescription = "Search"
                                        )
                                    }
                                }
                            )
                        }
                    }
                ) { contentPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding),
                        color = colors.background
                    ) {
                        SetupNavGraph(
                            navController = navController,
                            topbarStateChangeCallback = { showTopBar: Boolean ->
                                topbarState = showTopBar
                            }
                        )
                    }
                }
            }
        }
    }
}
