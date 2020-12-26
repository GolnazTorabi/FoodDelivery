package com.test.dindintest.food.app.view.menuList.drink

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.withState
import com.test.dindintest.food.app.view.menuList.adapter.FoodAdapter
import com.test.dindintest.food.app.view.sharedViewModel.SharedViewModel
import com.test.dindintest.R
import com.test.dindintest.databinding.FragmentDrinkBinding
import com.test.dindintest.util.BaseFragment
import com.test.dindintest.util.binding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable

@AndroidEntryPoint
class DrinkFragment : BaseFragment(R.layout.fragment_drink) {
    private val viewModel: DrinkViewModel by fragmentViewModel()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val binding: FragmentDrinkBinding by viewBinding()

    private lateinit var foodAdapter: FoodAdapter
    val snapHelper by lazy {
        LinearSnapHelper()
    }
    private var subscribeAdd: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        foodAdapter = FoodAdapter(requireActivity())
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.drinkList.layoutManager = layoutManager
        binding.drinkList.adapter = foodAdapter
        snapHelper.attachToRecyclerView(binding.drinkList)
        subscribeAdd = foodAdapter.clickEventAdd
            .subscribe {
                for (i in 0 until sharedViewModel.foodDrink.value?.size!!) {
                    if (it.first.name == sharedViewModel.foodDrink.value!![i].name) {
                        sharedViewModel.foodDrink.value!![i].count++
                        break
                    } else if (it.first.name != sharedViewModel.foodDrink.value!![i].name && i == sharedViewModel.foodDrink.value?.size!! - 1) {
                        sharedViewModel.sendMessageDrink(mutableListOf(it.first))
                    }
                }
            }
    }

    private fun EpoxyController.buildModels() = withState(viewModel) { state ->
        foodAdapter.fillData(state.foodList.toMutableList())
    }
}