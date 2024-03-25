package ru.topbun.gosporttest.data.source.remote.entities.food

import com.google.gson.annotations.SerializedName

data class FoodResponseDTO(
    @SerializedName("meals") val foodList: List<FoodDTO>
)