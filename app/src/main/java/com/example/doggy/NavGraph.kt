package com.example.doggy

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.doggy.Screens.Home.argUrl
import com.example.doggy.network.DogInfo
import com.example.doggy.screens.breeddetail.DogDetailScreen
import com.example.doggy.screens.home.HomeScreen
import com.example.doggy.screens.searchbar.SearchScreen
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG = "NavGraph"

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    topBarStateChangeCallback: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(
            route = Screens.Home.route
        ) {
            HomeScreen(
                homeViewModel = viewModel(),
                topBarStateChangeCallback = topBarStateChangeCallback,
                onDogClick = { dogInfo ->
                    Log.d(TAG, "SetupNavGraph: $dogInfo")
                    navController.navigate(Screens.Detail.createRoute(dogInfo))
                }
            )
        }
        composable(
            route = Screens.Detail.route,
            arguments = listOf(navArgument(argUrl) {
                type = DogInfoType
            })
        ) {
            @Suppress("DEPRECATION")
            val dogInfo: DogInfo = it.arguments?.getParcelable(argUrl)!!

            Log.d(TAG, "SetupNavGraph: imageUrl: $dogInfo")
            DogDetailScreen(dogInfo = dogInfo)
        }
        composable(
            route = Screens.Search.route,
        ) {
            SearchScreen(
                searchViewModel = viewModel(),
                onDogClick = { dogInfo ->
                    navController.navigate(Screens.Detail.createRoute(dogInfo))
                })
        }
    }
}

sealed class Screens(val route: String) {
    val argUrl = "imageUrl"

    object Home : Screens("home")

    object Search: Screens("search")

    object Detail : Screens("item/{$argUrl}") {
        fun createRoute(dogInfo: DogInfo): String {
            val json = Json.encodeToString(dogInfo)
            Log.d(TAG, "Json: $json")
            val argument: String = Uri.encode(json)
            Log.d(TAG, "Argument: $argument")
            return "item/$argument"
        }
    }
}

val DogInfoType = object : NavType<DogInfo>(
    isNullableAllowed = false
) {

    override fun put(bundle: Bundle, key: String, value: DogInfo) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): DogInfo {
        @Suppress("DEPRECATION")
        return bundle.getParcelable<DogInfo>(key) as DogInfo
    }

    override fun parseValue(value: String): DogInfo {
        return Json.decodeFromString(value)
    }

    // Only required when using Navigation 2.4.0-alpha07 and lower
    override val name = "DogInfo"
}