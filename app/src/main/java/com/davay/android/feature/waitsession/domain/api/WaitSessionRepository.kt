package com.davay.android.feature.waitsession.domain.api

interface WaitSessionRepository {
    suspend fun saveMovieIdListToDb(idList: List<Int>)
}