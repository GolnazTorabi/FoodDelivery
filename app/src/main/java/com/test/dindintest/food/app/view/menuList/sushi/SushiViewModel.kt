package com.test.dindintest.food.app.view.menuList.sushi

import androidx.lifecycle.ViewModel
import com.test.dindintest.food.app.view.menuList.pizza.FoodState
import com.test.dindintest.food.data.response.FoodResponse
import com.test.dindintest.food.domain.repository.FoodRepository
import com.test.dindintest.util.MvRxViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SushiViewModel (initialState: FoodState, private val repository: FoodRepository) :
    MvRxViewModel<FoodState>(initialState) {
    private val disposables = CompositeDisposable()

    init {
        getPizzaList()
    }

    private fun getPizzaList() {
        repository.getSushi().toObservable()
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
