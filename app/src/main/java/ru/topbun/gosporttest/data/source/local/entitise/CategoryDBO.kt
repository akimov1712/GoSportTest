package ru.topbun.gosporttest.data.source.local.entitise

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryDBO(
    @PrimaryKey
    val id: Int,
    val name: String
)
