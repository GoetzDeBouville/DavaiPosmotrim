package com.davai.extensions

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt

fun Int.pxToDp(): Int {
    val displayMetrics = Resources.getSystem().displayMetrics
    return (this * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}