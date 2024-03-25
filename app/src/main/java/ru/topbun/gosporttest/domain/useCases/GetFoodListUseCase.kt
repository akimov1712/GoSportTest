package ru.topbun.gosporttest.domain.useCases

import ru.topbun.gosporttest.domain.repository.CategoryRepository
import ru.topbun.gosporttest.domain.repository.FoodRepository

class GetFoodListUseCase(
    private val repository: FoodRepository
) {

    suspend operator fun invoke() = repository.getFoodList()

}