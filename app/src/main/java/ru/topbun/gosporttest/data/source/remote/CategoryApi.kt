package ru.topbun.gosporttest.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.topbun.gosporttest.data.source.remote.entities.category.CategoryResponseDTO
import ru.topbun.gosporttest.data.source.remote.entities.food.FoodResponseDTO

interface CategoryApi {


    @GET("/categories.php")
    suspend fun getCategoryList(): Result<CategoryResponseDTO>

}