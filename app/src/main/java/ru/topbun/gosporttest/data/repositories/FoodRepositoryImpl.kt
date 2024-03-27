package ru.topbun.gosporttest.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.topbun.gosporttest.data.RequestResponseMergeStrategy
import ru.topbun.gosporttest.data.source.local.dao.FoodDao
import ru.topbun.gosporttest.data.source.remote.FoodApi
import ru.topbun.gosporttest.data.source.remote.entities.food.FoodDTO
import ru.topbun.gosporttest.data.source.remote.entities.food.FoodResponseDTO
import ru.topbun.gosporttest.domain.RequestResult
import ru.topbun.gosporttest.domain.entities.FoodEntity
import ru.topbun.gosporttest.domain.repository.FoodRepository
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val dao: FoodDao,
    private val foodApi: FoodApi,
    private val mergeStrategy: RequestResponseMergeStrategy<List<FoodEntity>>
): FoodRepository {

    override suspend fun getFoodList(): Flow<RequestResult<List<FoodEntity>>> {
        val remoteData = getFoodFromServer()
        val cachedData = getFoodFromDatabase()
        return cachedData.combine(remoteData, mergeStrategy::merge)
    }

    override suspend fun getFoodCategory(category: String): Flow<RequestResult<List<FoodEntity>>> {
        return getFoodCategoryFromServer(category)
    }

    private suspend fun getFoodFromDatabase(): Flow<RequestResult<List<FoodEntity>>> {
        val data = dao.getFoodList().map { it.toEntity() }
        val requestResultData = RequestResult.Success(data)
        return flowOf<RequestResult<List<FoodEntity>>>(requestResultData)
    }

    private suspend fun getFoodCategoryFromServer(category: String): Flow<RequestResult<List<FoodEntity>>>{
        return flow{ emit(foodApi.getFoodCategory(category)) }
            .onEach { result ->
                processRecieveDataFromServer(result)
            }
            .map { result ->
                mapRecieveDataFromServer(result)
            }
    }

    private suspend fun getFoodFromServer(): Flow<RequestResult<List<FoodEntity>>>{
        return flow{ emit(foodApi.getFoodList()) }
            .onEach { result ->
                processRecieveDataFromServer(result)
            }
            .map { result ->
                mapRecieveDataFromServer(result)
            }
    }

    private fun mapRecieveDataFromServer(result: Result<FoodResponseDTO>) =
        if (result.isSuccess) {
            RequestResult.Success(result.getOrThrow().foodList.map { it.toEntity() })
        } else {
            RequestResult.Error("Ошибка при получении данных с сервера.")
        }

    private suspend fun processRecieveDataFromServer(result: Result<FoodResponseDTO>) {
        if (result.isSuccess) {
            saveNetResponseToCache(checkNotNull(result.getOrThrow().foodList))
        }
    }

    private suspend fun saveNetResponseToCache(data: List<FoodDTO>){
        val foodDboList = data.map { foods -> foods.toDBO() }
        dao.deleteFood()
        dao.addFoodList(foodDboList)
    }
}