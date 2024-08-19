package com.davay.android.core.data.network

import android.content.Context
import com.davay.android.core.data.network.model.NetworkParams
import com.davay.android.core.data.network.model.UserDataRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import javax.inject.Inject

class UserDataKtorNetworkClient @Inject constructor(
    context: Context,
    private val httpClient: HttpClient
) : HttpKtorNetworkClient<UserDataRequest, HttpResponse>(context) {

    override suspend fun sendResponseByType(request: UserDataRequest): HttpResponse {
        when (request) {
            is UserDataRequest.Registration -> {
                return httpClient.post {
                    url {
                        path(NetworkParams.API_USERS)
                    }
                    headers {
                        append(NetworkParams.DEVICE_ID, request.userData.userId)
                    }

                    contentType(ContentType.Application.Json)
                    setBody(request.userData)
                }
            }

            is UserDataRequest.ChangeName -> {
                return httpClient.put {
                    url {
                        path(NetworkParams.API_USERS)
                    }
                    headers {
                        append(NetworkParams.DEVICE_ID, request.userData.userId)
                    }

                    contentType(ContentType.Application.Json)
                    setBody(mapOf(NetworkParams.NAME to request.userData.name))
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: UserDataRequest,
        httpResponse: HttpResponse
    ): HttpResponse {
        return httpResponse
    }
}