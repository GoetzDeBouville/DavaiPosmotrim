package com.davay.android.feature.registration.data.network

import android.content.Context
import com.davay.android.core.data.network.HttpKtorNetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import javax.inject.Inject

class HttpRegistrationKtorClient @Inject constructor(
    context: Context,
    private val httpClient: HttpClient
) : HttpKtorNetworkClient<RegistrationRequest, RegistrationResponse>(context) {
    override suspend fun sendResponseByType(request: RegistrationRequest): HttpResponse {
        return httpClient.post {
            url {
                path(request.path)
            }
            headers {
                append(DEVICE_ID, request.userData.userId)
            }

            contentType(ContentType.Application.Json)
            setBody(request.userData)
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: RegistrationRequest,
        httpResponse: HttpResponse
    ): RegistrationResponse {
        return RegistrationResponse(httpResponse.body())
    }

    companion object {
        private const val DEVICE_ID = "Device-id"
    }
}