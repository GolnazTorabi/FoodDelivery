package com.test.dindintest.food.app.view.shopList.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.dindintest.food.data.response.FoodResponse
import com.test.dindintest.R
import com.test.dindintest.databinding.ItemFooterShopBinding
import com.test.dindintest.databinding.ItemShopBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CartAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: MutableList<FoodResponse> = mutableListOf()
    private val clickSubjectDelete = PublishSubject.create<Pair<FoodResponse, Int>>()


    companion object {
        const val FooterType = 1
        const val ListType = 0
    }


    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        return if (viewType == ListType) {
            val binding: ItemShopBinding =
                DataBindingUtil.inflate(layoutInflater!!, R.layout.item_shop, parent, false)
            ViewHolder(binding)
        } else {
            val binding: ItemFooterShopBinding =
                DataBindingUtil.inflate(layoutInflater!!, R.layout.item_footer_shop, parent, false)
            FooterViewHolder(binding)
        }


    }

    override fun getItemViewType(position: Int): Int { // Just as an example, return 0 or 2 depending on position
        return if (position == items.size - 1)
            FooterType
        else
            ListType

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setData(items[position])
        } else if (holder is FooterViewHolder) {
            holder.setSum()
        }
    }

    override fun getItemCount(): Int {
        return items.size + 1
    }

    fun fillData(items: MutableList<FoodResponse>) {
        this.items = items
        notifyDataSetChanged()
    }
    val clickEventDelete: Observable<Pair<FoodResponse, Int>> = clickSubjectDelete

    inner class ViewHolder(var binding: ItemShopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: FoodResponse) {
            binding.food = data
            binding.delete.setOnClickListener {
                clickSubjectDelete.onNext(Pair(data, layoutPosition))
            }
        }

    }

    inner class FooterViewHolder(var binding: ItemFooterShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setSum() {
            binding.countAll.text = items.size.toString()
        }
    }
}




