package ru.topbun.gosporttest.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.topbun.gosporttest.domain.repository.CategoryRepository
import ru.topbun.gosporttest.domain.repository.FoodRepository
import ru.topbun.gosporttest.domain.useCases.GetCategoryListUseCase
import ru.topbun.gosporttest.domain.useCases.GetFoodCategoryUseCase
import ru.topbun.gosporttest.domain.useCases.GetFoodListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun provideGetCategoryListUseCase(repository: CategoryRepository) = GetCategoryListUseCase(repository)


    @Provides
    @Singleton
    fun provideGetFoodCategoryUseCase(repository: FoodRepository) = GetFoodCategoryUseCase(repository)


    @Provides
    @Singleton
    fun provideGetFoodListUseCase(repository: FoodRepository) = GetFoodListUseCase(repository)

}