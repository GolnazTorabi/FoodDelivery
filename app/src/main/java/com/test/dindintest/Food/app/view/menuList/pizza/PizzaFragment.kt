package com.test.dindintest.Food.app.view.menuList.pizza

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.withState
import com.test.dindintest.Food.app.view.menuList.adapter.FoodAdapter
import com.test.dindintest.Food.app.view.sharedViewModel.SharedViewModel
import com.test.dindintest.R
import com.test.dindintest.databinding.FragmentPizzaBinding
import com.test.dindintest.util.BaseFragment
import com.test.dindintest.util.MvRxViewModel
import com.test.dindintest.util.binding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable

@AndroidEntryPoint
class PizzaFragment : BaseFragment(R.layout.fragment_pizza) {
    private val viewModel: PizzaViewModel by fragmentViewModel()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val binding: FragmentPizzaBinding by viewBinding()

    private var subscribeAdd: Disposable? = null


    private lateinit var foodAdapter: FoodAdapter
    private val snapHelper by lazy {
        LinearSnapHelper()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        foodAdapter = FoodAdapter(requireActivity())
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.pizzaList.layoutManager = layoutManager
        binding.pizzaList.adapter = foodAdapter
        snapHelper.attachToRecyclerView(binding.pizzaList)
        subscribeAdd = foodAdapter.clickEventAdd
            .subscribe {
                for (i in 0 until sharedViewModel.foodPizza.value?.size!!) {
                    if (it.first.name == sharedViewModel.foodPizza.value!![i].name) {
                        sharedViewModel.foodPizza.value!![i].count++
                        break
                    } else if (it.first.name != sharedViewModel.foodPizza.value!![i].name && i == sharedViewModel.foodPizza.value?.size!! - 1) {
                        sharedViewModel.sendMessagePizza(listOf(it.first))

                    }
                }
            }
    }

    private fun EpoxyController.buildModels() = withState(viewModel) { state ->
        foodAdapter.fillData(state.foodList.toMutableList())
    }
}