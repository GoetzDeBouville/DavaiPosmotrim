package com.davay.android.feature.createsession.data.network

import android.content.Context
import android.util.Log
import com.davay.android.data.network.ApiConstants
import com.davay.android.data.network.Response
import com.davay.android.extensions.isInternetReachable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.net.SocketTimeoutException
import javax.inject.Inject

class CreateSessionApiServiceImpl @Inject constructor(
    private val context: Context,
    private val client: HttpClient
) : CreateSessionApiService {
    override suspend fun getCollections(): Response {
        if (context.isInternetReachable().not()) {
            return createErrorResponse(ApiConstants.NO_INTERNET_CONNECTION_CODE)
        }
        return withContext(Dispatchers.IO) {
            executeApiCollectionCall()
        }
    }

    private suspend fun executeApiCollectionCall(): Response {
        return try {
            val response: HttpResponse = client.get("api/collections/")
            if (response.status.value == ApiConstants.SUCCESS_CODE) {
                CollectionListResponce(result = response.body() ?: emptyList()).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } else {
                createErrorResponse(response.status.value)
            }
        } catch (exception: IOException) {
            Log.v(ERROR_TAG, exception.toString())
            logAndCreateErrorResponse(exception)
        } catch (exception: JSONException) {
            Log.v(ERROR_TAG, exception.toString())
            logAndCreateErrorResponse(exception)
        } catch (exception: SocketTimeoutException) {
            Log.v(ERROR_TAG, exception.toString())
            logAndCreateErrorResponse(exception)
        }
    }

    override suspend fun getGenres(): Response {
        if (context.isInternetReachable().not()) {
            return createErrorResponse(ApiConstants.NO_INTERNET_CONNECTION_CODE)
        }
        return withContext(Dispatchers.IO) {
            executeApiGenresCall()
        }
    }

    private suspend fun executeApiGenresCall(): Response {
        return try {
            val response: HttpResponse = client.get("api/genres/")
            if (response.status.value == ApiConstants.SUCCESS_CODE) {
                GenreListResponse(result = response.body() ?: emptyList()).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } else {
                createErrorResponse(response.status.value)
            }
        } catch (exception: IOException) {
            Log.v(ERROR_TAG, exception.toString())
            logAndCreateErrorResponse(exception)
        } catch (exception: JSONException) {
            Log.v(ERROR_TAG, exception.toString())
            logAndCreateErrorResponse(exception)
        } catch (exception: SocketTimeoutException) {
            Log.v(ERROR_TAG, exception.toString())
            logAndCreateErrorResponse(exception)
        }
    }

    private fun createErrorResponse(code: Int): Response {
        return Response().apply {
            resultCode = code
        }
    }

    private fun logAndCreateErrorResponse(exception: Throwable): Response {
        val errorCode = when (exception) {
            is IOException -> ApiConstants.NO_INTERNET_CONNECTION_CODE
            is JSONException -> ApiConstants.BAD_REQUEST
            is SocketTimeoutException -> ApiConstants.NO_INTERNET_CONNECTION_CODE
            else -> ApiConstants.BAD_REQUEST
        }
        return createErrorResponse(errorCode)
    }

    private companion object {
        val ERROR_TAG = CreateSessionApiServiceImpl::class.simpleName
    }
}