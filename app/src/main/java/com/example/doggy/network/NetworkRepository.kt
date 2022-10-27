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
                Log.e("Api Call F", "error: $t")
            }
        })
    }
}
