package com.test.dindintest.Food.app.view.menuList.drink

import com.test.dindintest.Food.app.view.menuList.pizza.FoodState
import com.test.dindintest.Food.data.response.FoodResponse
import com.test.dindintest.Food.domain.repository.FoodRepository
import com.test.dindintest.util.MvRxViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DrinkViewModel(initialState: FoodState, private val repository: FoodRepository) :
    MvRxViewModel<FoodState>(initialState) {
    private val disposables = CompositeDisposable()

    init {
        getPizzaList()
    }

    private fun getPizzaList() {
        repository.getDrinks().toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<FoodResponse>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<FoodResponse>) {
                    setState { copy(foodList = t) }
                }

                override fun onError(e: Throwable) {
                }

            })


    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}