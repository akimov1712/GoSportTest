package ru.topbun.gosporttest.domain.useCases

import ru.topbun.gosporttest.domain.repository.CategoryRepository

class GetCategoryListUseCase(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke() = repository.getCategoryList()

}