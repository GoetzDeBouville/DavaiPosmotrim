package com.davay.android.data.di

import android.util.Log
import com.davay.android.data.network.ApiConstants
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
class NetworkModule {

    @Provides
    fun provideHttpClient() = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    useAlternativeNames = true
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                }
            )
        }
        install(HttpTimeout) {
            requestTimeoutMillis = CONNECTION_TIME_OUT_6_SEC
            connectTimeoutMillis = CONNECTION_TIME_OUT_6_SEC
            socketTimeoutMillis = SOCKET_TIME_OUT_20_SEC
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        defaultRequest {
            url(ApiConstants.BASE_URL)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    companion object {
        private const val CONNECTION_TIME_OUT_6_SEC = 6_000L
        private const val SOCKET_TIME_OUT_20_SEC = 20_000L
    }
}
