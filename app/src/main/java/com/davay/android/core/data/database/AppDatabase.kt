package com.davay.android.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davay.android.core.data.database.entity.MovieDetailsEntity
import com.davay.android.core.data.database.entity.SessionEntity
import com.davay.android.core.data.database.entity.SessionMovieCrossRef

@Database(
    entities = [
        SessionEntity::class,
        MovieDetailsEntity::class,
        SessionMovieCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
