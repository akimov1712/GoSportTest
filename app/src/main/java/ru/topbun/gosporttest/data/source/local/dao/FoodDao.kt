package ru.topbun.gosporttest.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.topbun.gosporttest.data.source.local.entitise.FoodDBO

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFoodList(list: List<FoodDBO>)

    @Query("DELETE FROM food")
    suspend fun deleteFood()

    @Query("SELECT * FROM food")
    fun getFoodList(): Flow<List<FoodDBO>>

}