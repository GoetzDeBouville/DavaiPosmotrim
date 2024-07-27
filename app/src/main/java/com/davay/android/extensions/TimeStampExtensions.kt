package com.davay.android.extensions

import android.icu.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

typealias timeStamp = Long

/**
 * Форматирует дату из Long (милисекунды) в строку, с учетом текущего года. Если год в дате равен
 * текущему, то он не указывается, в противном случае формат даты dd MMMM yyyy
 */
fun timeStamp.formatDateWithoutCurrentYear(): String {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val calendar = Calendar.getInstance().apply { time = Date(this@formatDateWithoutCurrentYear) }
    val year = calendar.get(Calendar.YEAR)

    val dateFormat = if (year < currentYear) {
        SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    } else {
        SimpleDateFormat("dd MMMM", Locale("ru"))
    }

    return dateFormat.format(this)
}

/**
 * Форматирует дату из Long (милисекунды) в строку, без привязки к текущему году
 */
fun timeStamp.formatDate(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    return dateFormat.format(date)
}
