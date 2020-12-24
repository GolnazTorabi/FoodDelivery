package com.test.dindintest.util.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.test.dindintest.R

@BindingAdapter(value = ["imageUrl"], requireAll = false)
fun ImageView.LoadImageUrl(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .error(R.drawable.ic_baseline_error_outline_24)
        .into(this)
}