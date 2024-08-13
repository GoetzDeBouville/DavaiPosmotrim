package com.davay.android.utils

import android.view.View

object DebounceUtil {
    private const val DEFAULT_DEBOUNCE_TIME = 600L

    fun View.setDebouncedOnClickListener(
        debounceTime: Long = DEFAULT_DEBOUNCE_TIME,
        action: () -> Unit
    ) {
        var runnable: Runnable? = null

        this.setOnClickListener {
            if (runnable != null) {
                handler.removeCallbacks(runnable!!)
            }
            runnable = Runnable {
                action()
            }
            handler.postDelayed(runnable!!, debounceTime)
        }
    }
}

