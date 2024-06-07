package com.davay.android.extensions

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import com.davay.android.R


fun Int.dpToPx(): Float {
    val metrics = Resources.getSystem().displayMetrics
    return this * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Int.formatMovieDuration(context: Context): String {
    val hours = this / MINUTES_IN_HOUR
    val remainingMinutes = this % MINUTES_IN_HOUR

    return when {
        hours > 0 && remainingMinutes > 0 -> context.getString(
            R.string.select_movies_hours_and_minutes,
            hours,
            remainingMinutes
        )

        hours > 0 -> context.getString(R.string.select_movies_hours, hours)
        else -> context.getString(R.string.select_movies_minutes, remainingMinutes)
    }
}

const val MINUTES_IN_HOUR = 60