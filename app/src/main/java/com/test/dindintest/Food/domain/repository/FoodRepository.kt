package com.test.dindintest.Food.domain.repository

import com.test.dindintest.Food.data.response.FoodResponse
import com.test.dindintest.Food.data.response.MenuDiscountResponse
import io.reactivex.Single

interface FoodRepository {
    fun getPizzas(): Single<List<FoodResponse>>
    fun getSushi(): Single<List<FoodResponse>>
    fun getDrinks(): Single<List<FoodResponse>>
    fun getDiscount() :Single<List<MenuDiscountResponse>>
}