package com.davay.android.feature.createsession.data.network

import android.content.Context
import com.davay.android.core.data.network.HttpKtorNetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path
import javax.inject.Inject

class HttpCreateSessionKtorClient @Inject constructor(
    context: Context,
    private val httpClient: HttpClient
) : HttpKtorNetworkClient<CreateSessionRequest, CreateSessionResponse>(context) {
    override suspend fun sendResponseByType(request: CreateSessionRequest): HttpResponse {
        return httpClient.get {
            url {
                path(request.path)
                parameter("format", "json")
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
        }
    }
}