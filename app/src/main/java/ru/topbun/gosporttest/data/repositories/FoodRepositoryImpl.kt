package ru.topbun.gosporttest.data.repositories

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
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

    private val flowResultFood = MutableSharedFlow<RequestResult<List<FoodEntity>>>(1, 10, BufferOverflow.DROP_OLDEST)

    override suspend fun getFoodList(): Flow<RequestResult<List<FoodEntity>>> {
        val cachedData = getFoodFromDatabase()
        val remoteData = getFoodFromServer()
        cachedData.combine(remoteData, mergeStrategy::merge).collect(flowResultFood::emit)
        return flowResultFood
    }

    override suspend fun getFoodCategory(category: String): Flow<RequestResult<List<FoodEntity>>> {
        val remoteData = getFoodCategoryFromServer().last()
        flowResultFood.emit(remoteData)
        return flowResultFood
    }

    private suspend fun getFoodFromDatabase(): Flow<RequestResult<List<FoodEntity>>> {
        val data = dao.getFoodList().map { foods ->
            foods.map { it.toEntity() }
        }.map {
            RequestResult.Success(it)
        }
        return data
    }

    private suspend fun getFoodCategoryFromServer(): Flow<RequestResult<List<FoodEntity>>>{
        return flow{ emit(foodApi.getFoodList()) }
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
        dao.addFoodList(foodDboList)
    }
}