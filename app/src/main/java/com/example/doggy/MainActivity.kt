package com.example.doggy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.doggy.network.DogInfo
import com.example.doggy.network.RetrofitAPI
import com.example.doggy.ui.theme.DoggyTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background
                ) {
                    var state: State by remember { mutableStateOf(State.Loading) }

                    getData(callback = { dogs ->
                        state = State.Success(dogs)
                    })
                    SetupNavGraph(navController = navController, state = state)
                }
            }
        }
    }
}

fun getData(callback: (List<DogInfo>) -> Unit) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thedogapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    val call = retrofitAPI.getDetail()

    call.enqueue(object : Callback<List<DogInfo>?> {
        override fun onResponse(
            call: Call<List<DogInfo>?>,
            response: Response<List<DogInfo>?>
        ) {
            Log.d("Api Call", "response: ${response.body()}")
            val dogDataFromApi = response.body()!!
            callback(dogDataFromApi)
        }

        override fun onFailure(call: Call<List<DogInfo>?>, t: Throwable) {
            Log.e("Api Call F", "error: $t")
        }
    })
}

sealed class State {
    object Loading : State()
    data class Success(val dogs: List<DogInfo>) : State()
}