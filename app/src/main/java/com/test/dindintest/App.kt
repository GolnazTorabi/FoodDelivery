package com.test.dindintest

import android.app.Application
import com.airbnb.mvrx.Mavericks.initialize
import com.airbnb.mvrx.MavericksView
import com.test.dindintest.food.data.api.FakeApi
import com.test.dindintest.food.data.repository.FoodRepositoryImpl
import com.test.dindintest.food.domain.repository.FoodRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(repository)
        }
    }
}

private val repository = module {
    single {
        provideRepository(provideFakeApi())
    }

}

fun provideRepository(fakeApi: FakeApi): FoodRepository {
    return FoodRepositoryImpl(fakeApi)
}
fun provideFakeApi(): FakeApi {
    return FakeApi()
}