package com.test.dindintest.util.repositoryModule

import com.test.dindintest.Food.data.repository.FoodRepositoryImpl
import com.test.dindintest.Food.domain.repository.FoodRepository
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