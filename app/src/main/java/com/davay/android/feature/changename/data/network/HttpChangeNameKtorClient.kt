package com.davay.android.feature.changename.data.network

import android.content.Context
import com.davay.android.core.data.network.HttpKtorNetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import javax.inject.Inject

class HttpChangeNameKtorClient @Inject constructor(
    context: Context,
    private val httpClient: HttpClient
) : HttpKtorNetworkClient<ChangeNameRequest, ChangeNameResponse>(context) {
    override suspend fun sendResponseByType(request: ChangeNameRequest): HttpResponse {
        return httpClient.put {
            url {
                path(request.path)
                parameter("format", "json")
            }
            headers {
                append("Device-id", request.userData.userId)
            }

            contentType(ContentType.Application.Json)
            setBody(mapOf("name" to request.userData.name))
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: ChangeNameRequest,
        httpResponse: HttpResponse
    ): ChangeNameResponse {
        return ChangeNameResponse(httpResponse.body())
    }
}