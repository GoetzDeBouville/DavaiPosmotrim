package com.davay.android.feature.createsession.data.network

import android.content.Context
import android.util.Log
import com.davay.android.BuildConfig
import com.davay.android.core.data.network.HttpKtorNetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path
import javax.inject.Inject

class HttpCreateSessionKtorClient @Inject constructor(
    context: Context,
    private val httpClient: HttpClient
) : HttpKtorNetworkClient<CreateSessionRequest, CreateSessionResponse>(context) {
    override suspend fun sendRequestByType(request: CreateSessionRequest): HttpResponse {
        return when (request) {
            is CreateSessionRequest.Session -> {
                if (BuildConfig.DEBUG) {
                    Log.v(
                        TAG,
                        "request -> ${request.requestBody.joinToString(", ")}\n${request.parameter}"
                    )
                }
                httpClient.post {
                    url {
                        path(request.path)
                        parameter("format", "json")
                    }

                    headers {
                        append(DEVICE_ID, request.userId)
                    }

                    setBody(mapOf(request.parameter to request.requestBody))
                }
            }

            else -> {
                httpClient.get {
                    url {
                        path(request.path)
                        parameter("format", "json")
                    }
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: CreateSessionRequest,
        httpResponse: HttpResponse
    ): CreateSessionResponse {
        return when (requestType) {
            is CreateSessionRequest.CollectionList -> {
                CreateSessionResponse.CollectionList(httpResponse.body())
            }

            is CreateSessionRequest.GenreList -> {
                CreateSessionResponse.GenreList(httpResponse.body())
            }

            is CreateSessionRequest.Session -> {
                CreateSessionResponse.Session(httpResponse.body())
            }
        }
    }

    private companion object {
        const val DEVICE_ID = "device-id"
        val TAG = HttpCreateSessionKtorClient::class.simpleName
    }
}