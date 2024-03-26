package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu

import ru.topbun.gosporttest.domain.entities.CategoryEntity
import ru.topbun.gosporttest.domain.entities.FoodEntity

sealed class FoodMenuState {

    data object Initial: FoodMenuState()
    data object Loading: FoodMenuState()

    data class ErrorGetCategory(val message: String): FoodMenuState()
    data class ErrorGetFoods(val message: String): FoodMenuState()

    data class ResultCategory(val category: List<CategoryEntity>): FoodMenuState()
    data class ResultFoods(val foods: List<FoodEntity>): FoodMenuState()

}