package com.test.dindintest.food.app.view.menuList.adapter

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.dindintest.food.data.response.FoodResponse
import com.test.dindintest.R
import com.test.dindintest.databinding.FoodItemBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class FoodAdapter(val activity: Activity) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var items: MutableList<FoodResponse> = mutableListOf()
    private val clickSubjectAdd = PublishSubject.create<Pair<FoodResponse, Int>>()

    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodAdapter.FoodViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: FoodItemBinding = DataBindingUtil.inflate(
            layoutInflater!!,
            R.layout.food_item,
            parent,
            false
        )
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.setData(items[position])
    }

    fun fillData(items: MutableList<FoodResponse>) {
        this.items = items
        notifyDataSetChanged()
    }

    val clickEventAdd: Observable<Pair<FoodResponse, Int>> = clickSubjectAdd

    inner class FoodViewHolder(var binding: FoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.M)
        fun setData(data: FoodResponse) {
            binding.food = data
            binding.foodPrice.setOnClickListener {
                binding.foodPrice.setBackgroundColor(activity.getColor(R.color.teal_700))
                binding.foodPrice.text = "Added + 1"
                val counterDisposable = Observable.timer(2, TimeUnit.SECONDS)
                    .map {
                        binding.foodPrice.setBackgroundColor(activity.getColor(R.color.design_default_color_primary))
                        binding.foodPrice.text = data.amount
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()


                compositeDisposable?.add(counterDisposable)
                clickSubjectAdd.onNext(Pair(data, layoutPosition))
            }
        }
    }
}


