package com.davay.android.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// не нужна
@Entity
class UserEntity(
    @PrimaryKey val userId: String,
    val name: String,
)
