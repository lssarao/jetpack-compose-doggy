package com.example.doggy.network

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("v1/breeds")
    suspend fun getDogData(): List<DogInfo>

    @GET("v1/breeds/search")
    fun searchBreedByName(@Query("q") name: String): Call<List<DogInfo>>
}

@Serializable
@Parcelize
@Entity(tableName = "Dog_Breeds")
data class DogInfo(
    @Embedded(prefix = "weight_") @SerializedName("weight") val weight: Weight?,
    @Embedded(prefix = "height_") @SerializedName("height") val height: Height?,
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("bred_for") val bredFor: String?,
    @SerializedName("breed_group") val breedGroup: String?,
    @SerializedName("life_span") val lifeSpan: String?,
    @SerializedName("temperament") val temperament: String?,
    @SerializedName("origin") val origin: String?,
    @SerializedName("reference_image_id") val referenceImageId: String?
) : Parcelable {

    val imageUrl: String
        get() = "https://cdn2.thedogapi.com/images/${referenceImageId}.jpg"
}

@Serializable
@Parcelize
data class Weight(
    @SerializedName("imperial") val imperial: String?,
    @SerializedName("metric") val metric: String?
) : Parcelable

@Serializable
@Parcelize
data class Height(
    @SerializedName("imperial") val imperial: String?,
    @SerializedName("metric") val metric: String?
) : Parcelable