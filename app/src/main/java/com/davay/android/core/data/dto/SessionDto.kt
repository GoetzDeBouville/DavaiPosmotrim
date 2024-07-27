package com.davay.android.core.data.dto

import com.google.gson.annotations.SerializedName

data class SessionDto(
    @SerializedName("id") val id: String,
    @SerializedName("users") val users: List<UserDto>,
    @SerializedName("matched_movies") val numberOfMatchedMovies: Int?,
    @SerializedName("date") val date: String,
    @SerializedName("status") val status: SessionStatusDto,
    @SerializedName("session_img") val imgUrl: String
)