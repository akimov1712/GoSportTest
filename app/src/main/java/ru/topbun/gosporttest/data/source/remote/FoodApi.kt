package ru.topbun.gosporttest.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.topbun.gosporttest.Const.API_PATH
import ru.topbun.gosporttest.data.source.remote.entities.food.FoodResponseDTO

interface FoodApi {

    @GET("$API_PATH/search.php")
    suspend fun getFoodList(
        @Query("s") title: String = ""
    ):Result<FoodResponseDTO>

    @GET("$API_PATH/filter.php")
    suspend fun getFoodCategory(
        @Query("c") category: String
    ):Result<FoodResponseDTO>

}