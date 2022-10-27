package com.example.doggy

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.doggy.Screens.List.argUrl
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG = "NavGraph"

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    state: State
) {
    NavHost(
        navController = navController,
        startDestination = Screens.List.route
    ) {
        composable(
            route = Screens.List.route
        ) {
            when (state) {
                is State.Loading -> {
                    WelcomePage()
                }
                is State.Success -> {
                    BreedList(allDog = state.dogs, onDogClick = { dogInfo ->

                        Log.d(TAG, "SetupNavGraph: : $dogInfo")
                        navController.navigate(Screens.Detail.createRoute(dogInfo))
                    })
                }
            }
        }
        composable(
            route = Screens.Detail.route,
            arguments = listOf(navArgument(argUrl) {
                type = DogInfoType
            })
        ) {
            val dogInfo: DogInfo = it.arguments?.getParcelable(argUrl)!!
            Log.d(TAG, "SetupNavGraph: imageUrl: $dogInfo")
            DogDetails(dogInfo = dogInfo, onHomeClick = {
                navController.popBackStack()
            })
        }
    }
}

sealed class Screens(val route: String) {
    val argUrl = "imageUrl"

    object List: Screens("list")
    object Detail: Screens("item/{$argUrl}") {
        fun createRoute(dogInfo: DogInfo): String {
            val json = Json.encodeToString(dogInfo)
            Log.d(TAG, "Json: $json")
            val argument: String = Uri.encode(json)
            Log.d(TAG, "Argument: $argument")
            return "item/$argument"
        }
    }
}