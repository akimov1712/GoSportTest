package ru.topbun.gosporttest.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.topbun.gosporttest.domain.RequestResult
import ru.topbun.gosporttest.domain.entities.FoodEntity

interface FoodRepository {

    suspend fun getFoodList(): Flow<RequestResult>
    suspend fun getFoodCategory(category: String): Flow<RequestResult>


}