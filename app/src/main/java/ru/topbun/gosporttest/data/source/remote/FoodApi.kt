package ru.topbun.gosporttest.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.topbun.gosporttest.data.source.remote.entities.category.CategoryResponseDTO
import ru.topbun.gosporttest.data.source.remote.entities.food.FoodResponseDTO

interface FoodApi {

    @GET("/search.php")
    suspend fun getFoodList(
        @Query("s") title: String = ""
    ):Result<FoodResponseDTO>

    @GET("/filter.php")
    suspend fun getFoodRecipe(
        @Query("c") category: String
    ):Result<FoodResponseDTO>

    @GET("/categories.php")
    suspend fun getCategoryList(): Result<CategoryResponseDTO>

}