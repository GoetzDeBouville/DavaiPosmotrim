package com.davay.android.extensions

import android.content.res.Resources
import android.util.DisplayMetrics


fun Int.dpToPx(): Float {
    val metrics = Resources.getSystem().displayMetrics
    return this * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}