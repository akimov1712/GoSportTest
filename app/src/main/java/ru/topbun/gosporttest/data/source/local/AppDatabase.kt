package ru.topbun.gosporttest.data.source.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.topbun.gosporttest.data.source.local.dao.CategoryDao
import ru.topbun.gosporttest.data.source.local.dao.FoodDao
import ru.topbun.gosporttest.data.source.local.entitise.CategoryDBO
import ru.topbun.gosporttest.data.source.local.entitise.FoodDBO

@Database(entities = [
    FoodDBO::class,
    CategoryDBO::class
], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun foodDao(): FoodDao

    companion object{

        private var INSTANCE: AppDatabase? = null
        private val DB_NAME = "goSportTest.db"

        fun getInstance(application: Application) = INSTANCE ?: synchronized(this){
            INSTANCE ?: createDatabase(application).also { INSTANCE = it }
        }

        private fun createDatabase(application: Application) = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DB_NAME
        ).build()

    }

}