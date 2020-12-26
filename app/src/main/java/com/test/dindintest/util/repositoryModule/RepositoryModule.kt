package com.test.dindintest.util.repositoryModule

import com.test.dindintest.food.data.repository.FoodRepositoryImpl
import com.test.dindintest.food.domain.repository.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun provideRepository(repo: FoodRepositoryImpl): FoodRepository = repo

}