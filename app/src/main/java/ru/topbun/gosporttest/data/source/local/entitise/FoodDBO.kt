package ru.topbun.gosporttest.data.source.local.entitise

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.topbun.gosporttest.domain.entities.FoodEntity

@Entity(tableName = "food")
data class FoodDBO(
    @PrimaryKey
    val id: Int,
    val title: String,
    val descr: String,
    val category: String,
    val image: String,
){
    fun toEntity() = FoodEntity(
        id = id,
        title = title,
        descr = descr,
        category = category,
        image = image,
    )
}
