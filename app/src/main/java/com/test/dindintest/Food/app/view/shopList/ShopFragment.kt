package com.test.dindintest.Food.app.view.shopList

import android.os.Bundle
import android.view.View
import com.test.dindintest.Food.app.view.shopList.adapter.ShopPagerAdapter
import com.test.dindintest.R
import com.test.dindintest.databinding.FragmentShopBinding
import com.test.dindintest.util.BaseFragment
import com.test.dindintest.util.binding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : BaseFragment(R.layout.fragment_shop) {

    private val binding: FragmentShopBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        binding.backImage.setOnClickListener { activity?.onBackPressed() }
    }

    private fun setupViewPager() {
        binding.tabLayout.setupWithViewPager(binding.pager)
        val adapter = ShopPagerAdapter(parentFragmentManager)
        binding.pager.adapter = adapter
    }
}