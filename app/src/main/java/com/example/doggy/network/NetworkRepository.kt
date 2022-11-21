package com.example.doggy.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository(private val retrofitAPI: RetrofitAPI) {

    suspend fun getBreedList(): Result<List<DogInfo>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching { retrofitAPI.getDetail() }
        }
    }

    fun searchBreedByName(name: String, callback: (dogInfoList: List<DogInfo>) -> Unit) {
        val call = retrofitAPI.searchBreedByName(name)

        call.enqueue(object : Callback<List<DogInfo>?> {
            override fun onResponse(
                call: Call<List<DogInfo>?>,
                response: Response<List<DogInfo>?>
            ) {
                Log.d("Search Call", "response: ${response.body()}")
                Log.d("Thread", "Search Api Call Thread: ${(Thread.currentThread().name)}")
                val searchedDataFromApi = response.body()!!
                callback(searchedDataFromApi)
            }

            override fun onFailure(call: Call<List<DogInfo>?>, t: Throwable) {
                Log.d("Search Call", "error: $t")
            }
        })
    }
}