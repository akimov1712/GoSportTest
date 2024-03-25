package ru.topbun.gosporttest.data.source.remote.entities.food

import com.google.gson.annotations.SerializedName

data class FoodDTO(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val title: String,
    @SerializedName("strInstructions") val descr: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strMealThumb") val image: String,
)