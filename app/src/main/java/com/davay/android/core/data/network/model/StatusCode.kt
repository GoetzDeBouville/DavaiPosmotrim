package com.davay.android.core.data.network.model

import com.davay.android.core.domain.models.ErrorType

@JvmInline
value class StatusCode(val code: Int)

fun StatusCode.mapToErrorType(): ErrorType {
    return when (code) {
        NO_CONNECTION_CODE -> ErrorType.NO_CONNECTION
        BAD_REQUEST_CODE -> ErrorType.BAD_REQUEST
        NOT_FOUND_CODE -> ErrorType.NOT_FOUND
        SERVER_ERROR_CODE -> ErrorType.SERVER_ERROR
        else -> ErrorType.UNKNOWN_ERROR
    }
}

private const val NO_CONNECTION_CODE = -1
private const val BAD_REQUEST_CODE = 400
private const val NOT_FOUND_CODE = 404
private const val SERVER_ERROR_CODE = 500