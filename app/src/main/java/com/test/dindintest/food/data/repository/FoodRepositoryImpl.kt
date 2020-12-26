package com.test.dindintest.food.data.repository

import com.test.dindintest.food.data.api.FakeApi
import com.test.dindintest.food.data.response.FoodResponse
import com.test.dindintest.food.data.response.MenuDiscountResponse
import com.test.dindintest.food.domain.repository.FoodRepository
import io.reactivex.Single
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(private val fakeApi: FakeApi) : FoodRepository {
    override fun getPizzas(): Single<List<FoodResponse>> {
        return fakeApi.getPizzasList()
    }

    override fun getSushi(): Single<List<FoodResponse>> {
        return fakeApi.getSushiList()
    }

    override fun getDrinks(): Single<List<FoodResponse>> {
        return fakeApi.getDrinksList()
    }

    override fun getDiscount(): Single<List<MenuDiscountResponse>> {
        return fakeApi.getDiscount()
    }


}