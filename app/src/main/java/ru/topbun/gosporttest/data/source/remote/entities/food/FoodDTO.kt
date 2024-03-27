package ru.topbun.gosporttest.data.source.remote.entities.food

import com.google.gson.annotations.SerializedName
import ru.topbun.gosporttest.data.source.local.entitise.FoodDBO
import ru.topbun.gosporttest.domain.entities.FoodEntity
import kotlin.random.Random

data class FoodDTO(
    @SerializedName("idMeal") val id: Int,
    @SerializedName("strMeal") val title: String,
    @SerializedName("strInstructions") val descr: String?,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strMealThumb") val image: String,
){
    fun toDBO() = FoodDBO(
        id =  id,
        title =  title,
        descr =  descr ?: "api не предоставляет описание при получении блюд по категорий",
        category =  category?: "",
        image =  image,
    )
    fun toEntity() = FoodEntity(
        id = id,
        title = title,
        descr = descr ?: "api не предоставляет описание при получении блюд по категорий",
        category = category?: "",
        image = image,
    )
}