package com.test.dindintest.food.app.view.menuList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.dindintest.food.data.response.MenuDiscountResponse
import com.test.dindintest.R
import com.test.dindintest.databinding.ToolbarItemBinding

class ToolbarAdapter : RecyclerView.Adapter<ToolbarAdapter.ToolbarViewHolder>() {

    private var items: MutableList<MenuDiscountResponse> = mutableListOf()

    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToolbarAdapter.ToolbarViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: ToolbarItemBinding = DataBindingUtil.inflate(
            layoutInflater!!,
            R.layout.toolbar_item,
            parent,
            false
        )
        return ToolbarViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ToolbarViewHolder, position: Int) {
        holder.setData(items[position])
    }

    fun fillData(items: MutableList<MenuDiscountResponse>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ToolbarViewHolder(var binding: ToolbarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: MenuDiscountResponse) {
            binding.discount = data
        }
    }
}


