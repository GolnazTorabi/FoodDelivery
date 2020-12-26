package com.test.dindintest.food.app.view.menuList

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.withState
import com.google.android.material.appbar.AppBarLayout
import com.test.dindintest.R
import com.test.dindintest.databinding.FragmentMenuBinding
import com.test.dindintest.food.app.view.menuList.adapter.MenuListPagerAdapter
import com.test.dindintest.food.app.view.menuList.adapter.ToolbarAdapter
import com.test.dindintest.food.app.view.sharedViewModel.SharedViewModel
import com.test.dindintest.util.BaseFragment
import com.test.dindintest.util.binding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MenuFragment : BaseFragment(R.layout.fragment_menu) {

    private val binding: FragmentMenuBinding by viewBinding()
    private val viewModel: MenuViewModel by fragmentViewModel()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()


    private var order: Int? = 0
    private var positionOfSlider: Int? = 0

    companion object {
        const val CORNER_SHRINK_RANGE = 0.15f
    }

    private lateinit var toolbarAdapter: ToolbarAdapter
    val snapHelper by lazy {
        LinearSnapHelper()
    }
    val layoutManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarStyle()
        initAdapter()
        checkOrders()
        setupViewPager()
        binding.addButton.setOnClickListener {
            val action = MenuFragmentDirections.actionBaseFragmentToShopFragment()
            findNavController().navigate(action)
        }
    }

    private fun checkOrders() {
        sharedViewModel.foodPizza.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                order = order?.plus(it.size)
                checkOrder()
            }
        })
        sharedViewModel.foodSushi.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                order = order?.plus(it.size)
                checkOrder()
            }
        })
        sharedViewModel.foodDrink.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                order = order?.plus(it.size)
                checkOrder()
            }
        })

    }

    private fun checkOrder() {
        if (order ?: 0 > 0) {
            binding.count.visibility = View.VISIBLE
            binding.count.text = order.toString()
        } else {
            binding.count.visibility = View.GONE

        }
    }

    private fun setupViewPager() {
        binding.tabLayout.setupWithViewPager(binding.pager)
        val menuListPagerAdapter = MenuListPagerAdapter(parentFragmentManager)
        binding.pager.adapter = menuListPagerAdapter
    }

    private fun EpoxyController.buildModels() = withState(viewModel) { state ->
        toolbarAdapter.fillData(state.discountList.toMutableList())

    }

    private fun initAdapter() {
        toolbarAdapter = ToolbarAdapter()
        binding.list.layoutManager = layoutManager
        binding.list.adapter = toolbarAdapter
        snapHelper.attachToRecyclerView(binding.list)
        initPageIndicator()
        setToolbarScrollListener()
    }

    private fun setToolbarScrollListener() {
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    try {
                        val centerView = snapHelper.findSnapView(layoutManager)
                        if (centerView != null) {
                            positionOfSlider = layoutManager.getPosition(centerView)
                            binding.pageIndicatorView.selection =
                                layoutManager.getPosition(centerView)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }

        })
        scrollItemsAutomatically()
    }

    private fun scrollItemsAutomatically() {
        val counterDisposable = Observable.interval(5, 5, TimeUnit.SECONDS)
            .map {
                it
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                try {

                    positionOfSlider =
                        if (positionOfSlider == ((binding.list.adapter?.itemCount)!! - 1)) {
                            0
                        } else {
                            layoutManager.findFirstVisibleItemPosition() + 1
                        }

                    binding.list.smoothScrollToPosition(positionOfSlider!!)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }, {
                it.printStackTrace()
            })

        compositeDisposable?.add(counterDisposable)
    }

    private fun initPageIndicator() {
        binding.pageIndicatorView.count = 3
        binding.pageIndicatorView.selection = 0
    }

    private fun setToolbarStyle() {
        val baseTopPadding = binding.toolbar.paddingTop
        var maxDeltaPadding = 0
        ViewCompat.setOnApplyWindowInsetsListener(binding.content) { _, insets ->
            maxDeltaPadding = insets.systemWindowInsetTop
            insets
        }
        var backgroundRadii: FloatArray? = null
        var maxRadius: FloatArray? = null
        val backgroundDrawable = (binding.toolbar.background as GradientDrawable?)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && backgroundDrawable != null) {
            backgroundRadii = backgroundDrawable.cornerRadii
            maxRadius = floatArrayOf(backgroundRadii!![0], backgroundRadii[1])
        }
        val appBarTotalScrollRange: Float by lazy {
            binding.appBarLayout.totalScrollRange.toFloat()
        }
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val percentOfScrollRange = (-verticalOffset / appBarTotalScrollRange)
            val deltaPadding = maxDeltaPadding * percentOfScrollRange
            val newTopPadding = baseTopPadding + deltaPadding.toInt()
            if (newTopPadding != binding.toolbar.paddingTop) {
                binding.toolbar.setPadding(
                    binding.toolbar.paddingLeft,
                    0,
                    binding.toolbar.paddingRight,
                    0
                )
                if (backgroundRadii != null && maxRadius != null) {
                    val radiusShrinkage = if (percentOfScrollRange > (1.0f - CORNER_SHRINK_RANGE)) {
                        (1.0f - percentOfScrollRange) / CORNER_SHRINK_RANGE
                    } else {
                        1.0f
                    }
                    backgroundRadii[0] = maxRadius[0] * radiusShrinkage
                    backgroundRadii[1] = maxRadius[1] * radiusShrinkage
                    backgroundRadii[2] = maxRadius[0] * radiusShrinkage
                    backgroundRadii[3] = maxRadius[1] * radiusShrinkage
                    backgroundDrawable!!.cornerRadii = backgroundRadii
                }
            }
        })
    }

}