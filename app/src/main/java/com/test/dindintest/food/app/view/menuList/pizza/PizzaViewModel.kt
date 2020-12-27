package com.test.dindintest.food.app.view.menuList.pizza

import androidx.hilt.lifecycle.ViewModelInject
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.test.dindintest.food.app.view.menuList.sushi.SushiViewModel
import com.test.dindintest.food.data.response.FoodResponse
import com.test.dindintest.food.domain.repository.FoodRepository
import com.test.dindintest.util.MvRxViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.test.dindintest.util.ext.addTo
import org.koin.android.ext.android.inject

data class FoodState(
    var foodList: List<FoodResponse> = emptyList()
) : MavericksState

class PizzaViewModel (initialState: FoodState, private val repository: FoodRepository) :
    MvRxViewModel<FoodState>(initialState) {
    private val disposables = CompositeDisposable()

    init {
        getPizzaList()
    }

    private fun getPizzaList() {
        repository.getPizzas().toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<List<FoodResponse>>{
                override fun onComplete() {
                    disposables.dispose()
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
    companion object : MavericksViewModelFactory<PizzaViewModel, FoodState> {

        override fun create(viewModelContext: ViewModelContext, state: FoodState): PizzaViewModel {
            val service: FoodRepository by viewModelContext.activity.inject()
            return PizzaViewModel(state, service)
        }
    }

    override fun onCleared() {
        disposables.clear()
    }
}
