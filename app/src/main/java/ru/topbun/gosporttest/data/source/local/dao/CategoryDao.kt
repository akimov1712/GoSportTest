package ru.topbun.gosporttest.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.topbun.gosporttest.data.source.local.entitise.CategoryDBO
import ru.topbun.gosporttest.data.source.local.entitise.FoodDBO

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategoryList(list: List<CategoryDBO>)

    @Query("DELETE FROM category")
    suspend fun deleteCategory()

    @Query("SELECT * FROM category")
     fun getCategoryList(): Flow<List<CategoryDBO>>

}