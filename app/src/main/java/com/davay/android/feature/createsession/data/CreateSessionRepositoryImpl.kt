package com.davay.android.feature.createsession.data

import android.util.Log
import com.davay.android.data.converters.toDomain
import com.davay.android.data.network.ApiConstants
import com.davay.android.domain.models.CompilationFilms
import com.davay.android.domain.models.ErrorType
import com.davay.android.domain.models.Genre
import com.davay.android.feature.createsession.data.network.CollectionListResponce
import com.davay.android.feature.createsession.data.network.CreateSessionApiService
import com.davay.android.feature.createsession.data.network.CreateSessionApiServiceImpl
import com.davay.android.feature.createsession.data.network.GenreListResponse
import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import com.davay.android.utils.network.ErrorHandler
import com.davay.android.utils.network.ErrorHandlerImpl
import com.davay.android.utils.network.Resource
import org.json.JSONException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class CreateSessionRepositoryImpl @Inject constructor(
    private val apiService: CreateSessionApiService
) : CreateSessionRepository {
    private val errorHandler: ErrorHandler = ErrorHandlerImpl()

    override suspend fun getCollections(): Resource<List<CompilationFilms>> {
        return try {
            val response = apiService.getCollections()
            when (response.resultCode) {
                ApiConstants.SUCCESS_CODE -> {
                    val collections = (response as? CollectionListResponce)?.result?.mapNotNull {
                        runCatching {
                            it.toDomain()
                        }.onFailure {
                            Log.v(ERROR_TAG, "mapping error -> ${it.localizedMessage}")
                        }.getOrNull()
                    }
                    Resource.Success(collections ?: emptyList())
                }

                else -> {
                    val error = errorHandler.handleErrorCode(response.resultCode)
                    Resource.Error(error.errorType)
                }
            }
        } catch (e: IOException) {
            Log.e(ERROR_TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
        } catch (e: JSONException) {
            Log.e(ERROR_TAG, e.toString())
            Resource.Error(ErrorType.UNEXPECTED)
        } catch (e: SocketTimeoutException) {
            Log.e(ERROR_TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
        }
    }

    override suspend fun getGenres(): Resource<List<Genre>> {
        return try {
            val response = apiService.getGenres()
            when (response.resultCode) {
                ApiConstants.SUCCESS_CODE -> {
                    val collections = (response as? GenreListResponse)?.result?.mapNotNull {
                        runCatching {
                            it.toDomain()
                        }.onFailure {
                            Log.v(ERROR_TAG, "mapping error -> ${it.localizedMessage}")
                        }.getOrNull()
                    }
                    Resource.Success(collections ?: emptyList())
                }

                else -> {
                    val error = errorHandler.handleErrorCode(response.resultCode)
                    Resource.Error(error.errorType)
                }
            }
        } catch (e: IOException) {
            Log.e(ERROR_TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
        } catch (e: JSONException) {
            Log.e(ERROR_TAG, e.toString())
            Resource.Error(ErrorType.UNEXPECTED)
        } catch (e: SocketTimeoutException) {
            Log.e(ERROR_TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
        }
    }

    private companion object {
        val ERROR_TAG = CreateSessionApiServiceImpl::class.simpleName
    }
}