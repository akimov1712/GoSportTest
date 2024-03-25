package ru.topbun.gosporttest.data.source.remote.entities.category

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("idCategory")val id: String,
    @SerializedName("strCategory")val name: String
)