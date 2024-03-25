package ru.topbun.gosporttest.data.source.local.entitise

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class FoodDBO(
    @PrimaryKey
    val id: Int,
    val title: String,
    val descr: String,
    val category: String,
    val price: Int
)
