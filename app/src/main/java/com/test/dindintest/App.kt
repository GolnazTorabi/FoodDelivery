package com.test.dindintest

import android.app.Application
import com.test.dindintest.food.app.view.menuList.MenuViewModel
import com.test.dindintest.food.data.repository.FoodRepositoryImpl
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
        FoodRepositoryImpl(get())
    }
    single {
        MenuViewModel(get(), get())
    }

}
