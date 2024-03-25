package ru.topbun.gosporttest.domain.useCases

import ru.topbun.gosporttest.domain.repository.FoodRepository

class GetFoodCategoryUseCase(
    private val repository: FoodRepository
) {

    suspend operator fun invoke(category: String) = repository.getFoodCategory(category)

}