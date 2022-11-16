package com.example.doggy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.doggy.ui.theme.DoggyTheme
import com.example.doggy.ui.theme.myColour2

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
                var topBarState by remember { mutableStateOf(false) }
                navController = rememberNavController()

                Scaffold(
                    topBar = {
                        if (topBarState) {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Dog Breeds",
                                        color = Color.White
                                    )
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = { navController.popBackStack() }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = "home",
                                            tint = Color.White
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = {
                                        navController.navigate("search")
                                    }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Search,
                                            contentDescription = "Search",
                                            tint = Color.White
                                        )
                                    }
                                },
                                backgroundColor = myColour2
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
                            topBarStateChangeCallback = { showTopBar: Boolean ->
                                topBarState = showTopBar
                            }
                        )
                    }
                }
            }
        }
    }
}
