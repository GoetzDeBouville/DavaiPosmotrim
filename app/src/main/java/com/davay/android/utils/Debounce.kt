package com.davay.android.utils

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounceUnitFun(
    coroutineScope: CoroutineScope,
    delayMillis: Long = DEFAULT_DELAY_600,
    useLastParam: Boolean = false
): (T, (T) -> Unit) -> Unit {
    var debounceJob: Job? = null
    return { param: T, finalAction: (T) -> Unit ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                if (useLastParam) {
                    delay(delayMillis)
                }
                finalAction(param)
                if (!useLastParam) {
                    delay(delayMillis)
                }
            }
        }
    }
}

fun View.setOnDebouncedClickListener(
    coroutineScope: CoroutineScope,
    delayMillis: Long = DEFAULT_DELAY_600,
    useLastParam: Boolean = false,
    onClick: (View) -> Unit,
) {
    var debounceJob: Job? = null

    setOnClickListener { view ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                if (useLastParam) {
                    delay(delayMillis)
                }
                onClick(view)
                if (!useLastParam) {
                    delay(delayMillis)
                }
            }
        }
    }
}

const val DEFAULT_DELAY_600 = 600L

