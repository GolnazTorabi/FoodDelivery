package com.test.dindintest.food.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FoodResponse(
    var name: String,
    var detail: String,
    var size: String,
    var Image: String,
    var amount: String,
    var count: Int,
    var type: String
):Parcelable