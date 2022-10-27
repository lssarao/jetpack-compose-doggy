package com.example.doggy.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiBuilder {

    fun build(): RetrofitAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitAPI::class.java)
    }
}
