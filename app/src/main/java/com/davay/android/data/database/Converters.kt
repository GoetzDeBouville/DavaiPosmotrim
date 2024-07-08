package com.davay.android.data.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toStringFromList(value: List<String>): String {
        return value.joinToString(separator = ";")
    }

    @TypeConverter
    fun toListFromString(value: String): List<String> {
        return value.split(";")
    }
}
