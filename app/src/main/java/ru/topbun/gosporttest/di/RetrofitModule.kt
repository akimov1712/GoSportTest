package ru.topbun.gosporttest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.topbun.gosporttest.data.source.remote.RetrofitFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideFoodApi() = RetrofitFactory.foodApi

    @Provides
    @Singleton
    fun provideCategoryApi() = RetrofitFactory.categoryApi

}