package com.davay.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davay.android.data.database.entity.MovieDetailsEntity
import com.davay.android.data.database.entity.SessionEntity
import com.davay.android.data.database.entity.SessionMovieCrossRef

@Database(
    entities = [SessionEntity::class, MovieDetailsEntity::class, SessionMovieCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
