package ru.topbun.gosporttest.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.topbun.gosporttest.data.source.local.AppDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideFoodDao(application: Application) = AppDatabase.getInstance(application).foodDao()

    @Provides
    @Singleton
    fun provideCategoryDao(application: Application) = AppDatabase.getInstance(application).categoryDao()


}