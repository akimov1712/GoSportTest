package ru.topbun.gosporttest.data.repositories

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.topbun.gosporttest.data.RequestResponseMergeStrategy
import ru.topbun.gosporttest.data.source.local.dao.CategoryDao
import ru.topbun.gosporttest.data.source.remote.CategoryApi
import ru.topbun.gosporttest.data.source.remote.RetrofitFactory.foodApi
import ru.topbun.gosporttest.data.source.remote.entities.category.CategoryDTO
import ru.topbun.gosporttest.data.source.remote.entities.category.CategoryResponseDTO
import ru.topbun.gosporttest.domain.RequestResult
import ru.topbun.gosporttest.domain.entities.CategoryEntity
import ru.topbun.gosporttest.domain.entities.FoodEntity
import ru.topbun.gosporttest.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao,
    private val categoryApi: CategoryApi,
    private val mergeStrategy: RequestResponseMergeStrategy<List<CategoryEntity>>
): CategoryRepository {

    override suspend fun getCategoryList(): Flow<RequestResult<List<CategoryEntity>>> {
        val cachedData = getCategoryFromDatabase()
        val remoteData = getCategoryFromServer()
        return cachedData.combine(remoteData, mergeStrategy::merge)
    }

    private suspend fun getCategoryFromDatabase(): Flow<RequestResult<List<CategoryEntity>>> {
        val data = dao.getCategoryList().map { categories ->
            categories.map { it.toEntity() }
        }.map {
            if (it.isEmpty()){
                RequestResult.Error ()
            }else {
                RequestResult.Success(it)
            }
        }
        return data
    }

    private suspend fun getCategoryFromServer(): Flow<RequestResult<List<CategoryEntity>>>{
        val data = flow{ emit(categoryApi.getCategoryList()) }
            .onEach { result ->
                processRecieveDataFromServer(result)
            }
            .map { result ->
                mapRecieveDataFromServer(result)
            }
        return data
    }

    private fun mapRecieveDataFromServer(result: Result<CategoryResponseDTO>) =
        if (result.isSuccess) {
            RequestResult.Success(result.getOrThrow().categories.map { it.toEntity() })
        } else {
            RequestResult.Error()
        }

    private suspend fun processRecieveDataFromServer(result: Result<CategoryResponseDTO>) {
        if (result.isSuccess) {
            saveNetResponseToCache(checkNotNull(result.getOrThrow().categories))
        }
    }

    private suspend fun saveNetResponseToCache(data: List<CategoryDTO>){
        val foodDboList = data.map { foods -> foods.toDBO() }
        dao.addCategoryList(foodDboList)
    }

}