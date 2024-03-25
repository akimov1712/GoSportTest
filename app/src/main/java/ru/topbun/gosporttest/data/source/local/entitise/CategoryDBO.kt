package ru.topbun.gosporttest.data.source.local.entitise

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.topbun.gosporttest.domain.entities.CategoryEntity

@Entity(tableName = "category")
data class CategoryDBO(
    @PrimaryKey
    val id: Int,
    val name: String
){
    fun toEntity() = CategoryEntity(
        id = id,
        name = name,
        selected = false
    )
}
