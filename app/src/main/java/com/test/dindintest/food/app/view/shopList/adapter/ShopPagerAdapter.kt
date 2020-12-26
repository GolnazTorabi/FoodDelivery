package com.test.dindintest.food.app.view.shopList.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.test.dindintest.food.app.view.shopList.cart.CartFragment
import com.test.dindintest.food.app.view.shopList.information.InformationFragment
import com.test.dindintest.food.app.view.shopList.orders.OrdersFragment

@Suppress("DEPRECATION")
class ShopPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CartFragment()
            1 -> OrdersFragment()
            2 -> InformationFragment()
            else -> CartFragment()
        }
    }

    override fun getCount(): Int = 3

}