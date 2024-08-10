package com.davay.android.feature.waitsession.domain

import com.davay.android.feature.waitsession.domain.api.WaitSessionRepository
import javax.inject.Inject

class SaveMovieIdListToDbUseCase @Inject constructor(
    private val repository: WaitSessionRepository
) {
    suspend fun execute(idList: List<Int>) {
        repository.saveMovieIdListToDb(idList)
    }
}