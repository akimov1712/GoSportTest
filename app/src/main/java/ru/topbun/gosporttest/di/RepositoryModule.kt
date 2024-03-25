package ru.topbun.gosporttest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.topbun.gosporttest.data.repositories.CategoryRepositoryImpl
import ru.topbun.gosporttest.data.repositories.FoodRepositoryImpl
import ru.topbun.gosporttest.domain.repository.CategoryRepository
import ru.topbun.gosporttest.domain.repository.FoodRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindFoodRepository(impl: FoodRepositoryImpl): FoodRepository


    @Binds
    @Singleton
    fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

}