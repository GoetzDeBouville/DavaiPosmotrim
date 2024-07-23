package com.davay.android.feature.createsession.data.network

import com.davay.android.data.dto.GenreDto
import com.davay.android.data.network.Response

class GenreListResponse(val result: List<GenreDto>) : Response()