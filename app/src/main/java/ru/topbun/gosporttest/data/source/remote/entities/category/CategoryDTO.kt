package ru.topbun.gosporttest.data.source.remote.entities.category

import com.google.gson.annotations.SerializedName
import ru.topbun.gosporttest.data.source.local.entitise.CategoryDBO

data class CategoryDTO(
    @SerializedName("idCategory")val id: Int,
    @SerializedName("strCategory")val name: String
) {
    fun toDBO() = CategoryDBO(
        id = id,
        name = name
    )
}