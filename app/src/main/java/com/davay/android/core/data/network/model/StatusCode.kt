package com.davay.android.core.data.network.model

import com.davay.android.core.domain.models.ErrorType

class StatusCode(private val code: Int) {

    fun mapToErrorType(): ErrorType {
        return when (code) {
            NetworkParams.NO_CONNECTION_CODE -> ErrorType.NO_CONNECTION
            NetworkParams.BAD_REQUEST_CODE -> ErrorType.BAD_REQUEST
            NetworkParams.NOT_FOUND_CODE -> ErrorType.NOT_FOUND
            NetworkParams.SERVER_ERROR_CODE -> ErrorType.SERVER_ERROR
            else -> ErrorType.UNKNOWN_ERROR
        }
    }
}