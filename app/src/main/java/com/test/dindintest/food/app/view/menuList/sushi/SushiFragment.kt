package com.test.dindintest.food.app.view.menuList.sushi

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.test.dindintest.R
import com.test.dindintest.databinding.FragmentSushiBinding
import com.test.dindintest.food.app.view.menuList.adapter.FoodAdapter
import com.test.dindintest.food.app.view.sharedViewModel.SharedViewModel
import com.test.dindintest.util.BaseFragment
import com.test.dindintest.util.binding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable

class SushiFragment : BaseFragment(R.layout.fragment_sushi), MavericksView {
    private val viewModel: SushiViewModel by fragmentViewModel()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val binding: FragmentSushiBinding by viewBinding()
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
        binding.sushiList.layoutManager = layoutManager
        binding.sushiList.adapter = foodAdapter
        snapHelper.attachToRecyclerView(binding.sushiList)
        subscribeAdd = foodAdapter.clickEventAdd
            .subscribe {
                loop@ for (i in 0 until sharedViewModel.foodSushi.value?.size!!) {
                    if (it.first.name == sharedViewModel.foodSushi.value!![i].name) {
                        sharedViewModel.foodSushi.value!![i].count++
                        break@loop
                    } else if (it.first.name != sharedViewModel.foodSushi.value!![i].name && i == sharedViewModel.foodSushi.value?.size!! - 1) {
                        sharedViewModel.sendMessageSushi(mutableListOf(it.first))
                        break@loop
                    }
                }
            }
    }


    override fun invalidate() = withState(viewModel) { state ->
        foodAdapter.fillData(state.foodList.toMutableList())
    }
}