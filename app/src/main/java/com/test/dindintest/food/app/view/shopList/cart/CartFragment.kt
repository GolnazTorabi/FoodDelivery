package com.test.dindintest.food.app.view.shopList.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.test.dindintest.food.app.view.sharedViewModel.SharedViewModel
import com.test.dindintest.food.app.view.shopList.cart.adapter.CartAdapter
import com.test.dindintest.R
import com.test.dindintest.databinding.FragmentCartBinding
import com.test.dindintest.util.BaseFragment
import com.test.dindintest.util.binding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable


class CartFragment : BaseFragment(R.layout.fragment_cart) {
    private val binding: FragmentCartBinding by viewBinding()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter
    private val snapHelper by lazy {
        LinearSnapHelper()
    }
    private var subscribeDelete: Disposable? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeData()
    }

    private fun initAdapter() {
        cartAdapter = CartAdapter()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.shopList.layoutManager = layoutManager
        binding.shopList.adapter = cartAdapter
        snapHelper.attachToRecyclerView(binding.shopList)
        subscribeDelete = cartAdapter.clickEventDelete
            .subscribe {
                when (it.first.type) {
                    "pizza" -> sharedViewModel.foodPizza.value?.removeAt(it.second)
                    "sushi" -> sharedViewModel.foodSushi.value?.removeAt(it.second)
                    "drink" -> sharedViewModel.foodDrink.value?.removeAt(it.second)
                }
            }
    }

    private fun observeData() {
        sharedViewModel.foodPizza.observe(viewLifecycleOwner, Observer {
            cartAdapter.fillData(it.toMutableList())
        })
        sharedViewModel.foodSushi.observe(viewLifecycleOwner, Observer {
            cartAdapter.fillData(it.toMutableList())
        })
        sharedViewModel.foodDrink.observe(viewLifecycleOwner, Observer {
            cartAdapter.fillData(it.toMutableList())
        })
    }
}