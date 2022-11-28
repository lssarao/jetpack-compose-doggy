package com.example.doggy.network

import android.util.Log
import com.example.doggy.database.DogBreedsDao
import com.example.doggy.database.TimeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "NetworkRepository"

class NetworkRepository(
    private val retrofitAPI: RetrofitAPI,
    private val dogBreedsDao: DogBreedsDao,
    private val timeManager: TimeManager
) {

    suspend fun getBreedList(): Result<List<DogInfo>> {
        return withContext(Dispatchers.IO) {
            // check if dogs info exists in database
            val dogsFromDatabase = dogBreedsDao.getDogData()
            Log.d(TAG, "Dogs in database ${dogsFromDatabase.size}")

            if (dogsFromDatabase.isEmpty()) {
                // do not have dogs
                // fetch from network
                getDogsFromApi()
            } else {
                if (timeManager.isCacheValid()) {
                    // show data from db
                    Result.success(dogsFromDatabase)
                } else {
                    // make api call
                    getDogsFromApi()
                }
            }
        }
    }

    private suspend fun getDogsFromApi() = kotlin.runCatching {
        val dogsFromApi = retrofitAPI.getDogData()
        dogBreedsDao.insertAll(dogsFromApi)
        timeManager.updateCacheTimestamp()
        Log.d(TAG, "Data Save in Database: $dogsFromApi")
        dogsFromApi
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