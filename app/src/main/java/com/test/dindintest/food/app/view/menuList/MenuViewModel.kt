package com.test.dindintest.food.app.view.menuList

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.test.dindintest.food.data.response.MenuDiscountResponse
import com.test.dindintest.food.domain.repository.FoodRepository
import com.test.dindintest.util.MvRxViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject


data class MenuDiscountState(
    val discountList: List<MenuDiscountResponse> = emptyList()
) : MavericksState

class MenuViewModel(initialState: MenuDiscountState, private val repository: FoodRepository) :
    MvRxViewModel<MenuDiscountState>(initialState) {
    private val disposables = CompositeDisposable()

    init {
        getDiscountList()
    }

    private fun getDiscountList() {
        repository.getDiscount().toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<MenuDiscountResponse>> {
                override fun onComplete() {
                    disposables.dispose()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<MenuDiscountResponse>) {
                    setState { copy(discountList = t) }
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    companion object : MavericksViewModelFactory<MenuViewModel, MenuDiscountState> {

        override fun create(
            viewModelContext: ViewModelContext,
            state: MenuDiscountState
        ): MenuViewModel {
            val service: FoodRepository by viewModelContext.activity.inject()
            return MenuViewModel(state, service)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}