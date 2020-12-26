package com.test.dindintest.food.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuDiscountResponse(
    var name: String,
    var Image: String
):Parcelable