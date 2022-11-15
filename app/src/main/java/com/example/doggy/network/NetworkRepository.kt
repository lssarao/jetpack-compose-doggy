package com.example.doggy.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository(private val retrofitAPI: RetrofitAPI) {

    fun getBreedList(callback: (dogInfoList: List<DogInfo>) -> Unit) {
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
                Log.e("Api Call", "error: $t")
            }
        })
    }

    fun searchBreedByName(name: String, callback: (dogInfoList: List<DogInfo>) -> Unit) {
        val call = retrofitAPI.searchBreedByName(name)

        call.enqueue(object : Callback<List<DogInfo>?> {
            override fun onResponse(
                call: Call<List<DogInfo>?>,
                response: Response<List<DogInfo>?>
            ) {
                Log.d("Search Call", "response: ${response.body()}")
                val searchedDataFromApi = response.body()!!
                callback(searchedDataFromApi)
            }

            override fun onFailure(call: Call<List<DogInfo>?>, t: Throwable) {
                Log.d("Search Call", "error: $t")
            }
        })
    }
}
/*
    fun getImage(imageId: String, callback: (dogImage: DogImage) -> Unit) {
        val call = retrofitAPI.getImage(imageId)

        call.enqueue(object : Callback<DogImage> {
            override fun onResponse(call: Call<DogImage>, response: Response<DogImage>) {
                Log.d("Get Image Request", "response: ${response.body()}")
                val imageDataFromApi = response.body()!!
                callback(imageDataFromApi)
            }

            override fun onFailure(call: Call<DogImage>, t: Throwable) {
                Log.d("Get Image Request", "error: $t")
            }

        })
    }
}
*/