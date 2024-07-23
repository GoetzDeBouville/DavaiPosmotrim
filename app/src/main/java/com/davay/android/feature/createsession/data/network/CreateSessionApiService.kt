package com.davay.android.feature.createsession.data.network

import com.davay.android.data.network.Response

interface CreateSessionApiService {
    suspend fun getCollections(): Response
    suspend fun getGenres(): Response
}