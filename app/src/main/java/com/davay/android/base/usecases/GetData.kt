package com.davay.android.base.usecases

import com.davay.android.feature.coincidences.ErrorType
import com.davay.android.utils.Result

interface GetData<T> {

    suspend fun getData(): Result<List<T>, ErrorType>
}