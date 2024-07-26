package com.davay.android.utils.network

import com.davay.android.core.data.network.ApiConstants
import com.davay.android.core.domain.models.ErrorType
import retrofit2.HttpException

class ErrorHandlerImpl : ErrorHandler {
    override fun handleErrorCode(resultCode: Int) = Resource.Error(
        when (resultCode) {
            ApiConstants.BAD_REQUEST -> ErrorType.BAD_REQUEST
            ApiConstants.UNAUTHORIZED -> ErrorType.UNAUTHORIZED
            ApiConstants.FORBIDDEN -> ErrorType.FORBIDDEN
            ApiConstants.NOT_FOUND -> ErrorType.NOT_FOUND
            ApiConstants.CONFLICT -> ErrorType.CONFLICT
            ApiConstants.UNPROCESSABLE_ENTITY -> ErrorType.UNPROCESSABLE_ENTITY
            ApiConstants.INTERNAL_SERVER_ERROR -> ErrorType.INTERNAL_SERVER_ERROR
            ApiConstants.BAD_GATEWAY -> ErrorType.BAD_GATEWAY
            else -> ErrorType.UNEXPECTED
        }
    )

    override fun handleHttpException(exception: HttpException) = handleErrorCode(exception.code())
}
