package ru.topbun.gosporttest.data.source.remote.entities.food

import com.google.gson.annotations.SerializedName
import ru.topbun.gosporttest.data.source.local.entitise.FoodDBO
import kotlin.random.Random

data class FoodDTO(
    @SerializedName("idMeal") val id: Int,
    @SerializedName("strMeal") val title: String,
    @SerializedName("strInstructions") val descr: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strMealThumb") val image: String,
){
    fun toDBO() = FoodDBO(
        id =  id,
        title =  title,
        descr =  descr,
        category =  category,
        image =  image,
        price = Random.nextInt(0,500)
    )
}