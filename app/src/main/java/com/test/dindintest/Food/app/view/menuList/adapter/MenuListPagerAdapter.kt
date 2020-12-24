package com.test.dindintest.Food.app.view.menuList.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.test.dindintest.Food.app.view.menuList.drink.DrinkFragment
import com.test.dindintest.Food.app.view.menuList.pizza.PizzaFragment
import com.test.dindintest.Food.app.view.menuList.sushi.SushiFragment

@Suppress("DEPRECATION")
class MenuListPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PizzaFragment()
            1 -> SushiFragment()
            2 -> DrinkFragment()
            else -> PizzaFragment()
        }
    }

    override fun getCount(): Int = 3

}