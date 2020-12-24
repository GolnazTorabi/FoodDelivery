package com.test.dindintest.Food.app.view.menuList

import com.airbnb.mvrx.MavericksState
import com.test.dindintest.Food.data.response.MenuDiscountResponse
import com.test.dindintest.Food.domain.repository.FoodRepository
import com.test.dindintest.util.MvRxViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


data class MenuDiscountState(
    var discountList: List<MenuDiscountResponse> = emptyList()
) : MavericksState

class MenuViewModel(initialState: MenuDiscountState, private val repository: FoodRepository) :
    MvRxViewModel<MenuDiscountState>(initialState) {
    init {
        getDiscountList()
    }

    private fun getDiscountList() {
        repository.getDiscount().toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<MenuDiscountResponse>> {
                override fun onComplete() {
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

}