package com.davay.android.feature.createsession.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import com.davay.android.feature.createsession.domain.model.SessionType
import kotlinx.coroutines.flow.first

class CreateSessionWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: CreateSessionRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val sessionType = inputData.getString("SESSION_TYPE") ?: return Result.failure()
        val requestBody =
            inputData.getStringArray("REQUEST_BODY")?.toList() ?: return Result.failure()

        return try {
            val result =
                repository.createSession(SessionType.valueOf(sessionType), requestBody).first()
            if (result is com.davay.android.core.domain.models.Result.Success) {
                Result.success()
            } else {
                Result.retry() // В случае ошибки еще раз запускаем воркер
            }
        } catch (e: Exception) {
            Result.retry() // В случае исключения еще раз запускаем воркер
        }
    }


}