package com.test.dindintest.Food.app.view.sharedViewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.dindintest.Food.data.response.FoodResponse

class SharedViewModel @ViewModelInject constructor() : ViewModel() {
    val foodPizza = MutableLiveData<MutableList<FoodResponse>>()

    fun sendMessagePizza(foodItem: MutableList<FoodResponse>) {
        foodPizza.value = foodItem
    }

    val foodSushi = MutableLiveData<MutableList<FoodResponse>>()

    fun sendMessageSushi(foodItem: MutableList<FoodResponse>) {
        foodSushi.value = foodItem
    }

    val foodDrink = MutableLiveData<MutableList<FoodResponse>>()

    fun sendMessageDrink(foodItem: MutableList<FoodResponse>) {
        foodDrink.value = foodItem
    }

}