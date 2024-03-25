package ru.topbun.gosporttest.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.topbun.gosporttest.domain.entities.CategoryEntity

interface CategoryRepository {

    suspend fun getCategoryList(): Flow<List<CategoryEntity>>

}