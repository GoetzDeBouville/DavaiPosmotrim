package com.davay.android.data.database

import androidx.room.TypeConverter
import com.davay.android.domain.models.Genre
import com.davay.android.domain.models.User

class Converters {
    @TypeConverter
    fun toStringFromList(value: List<String>): String {
        return value.joinToString(separator = ";")
    }

    @TypeConverter
    fun toListFromString(value: String): List<String> {
        return value.split(";")
    }

    @TypeConverter
    fun toStringFromGenresList(value: List<Genre>): String {
        return value.joinToString(separator = ";") {
            it.name
        }
    }

    @TypeConverter
    fun toGenresListFromString(value: String): List<Genre> {
        return value.split(";").map {
            Genre(it)
        }
    }
}
