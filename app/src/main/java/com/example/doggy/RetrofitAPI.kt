package com.example.doggy

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {
    @GET("v1/breeds")
    fun getDetail(): Call<List<DogInfo>>
}

@Serializable
@Parcelize
data class DogInfo(
    @SerializedName("weight") val weight: Weight,
    @SerializedName("height") val height: Height,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("bred_for") val bredFor: String?,
    @SerializedName("breed_group") val breedGroup: String?,
    @SerializedName("life_span") val lifeSpan: String,
    @SerializedName("temperament") val temperament: String,
    @SerializedName("origin") val origin: String?,
    @SerializedName("reference_image_id") val referenceImageId: String,
    @SerializedName("image") val image: Image
) : Parcelable

@Serializable
@Parcelize
data class Weight(
    @SerializedName("imperial") val imperial: String,
    @SerializedName("metric") val metric: String
) : Parcelable

@Serializable
@Parcelize
data class Height(
    @SerializedName("imperial") val imperial: String,
    @SerializedName("metric") val metric: String
) : Parcelable

@Serializable
@Parcelize
data class Image(
    @SerializedName("id") val id: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("url") val url: String
) : Parcelable

val DogInfoType = object : NavType<DogInfo>(
    isNullableAllowed = false
) {

    override fun put(bundle: Bundle, key: String, value: DogInfo) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): DogInfo {
        return bundle.getParcelable<DogInfo>(key) as DogInfo
    }

    override fun parseValue(value: String): DogInfo {
        return Json.decodeFromString(value)
    }

    // Only required when using Navigation 2.4.0-alpha07 and lower
    override val name = "DogInfo"
}