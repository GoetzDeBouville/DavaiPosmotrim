package com.davay.android.feature.changename.domain.usecase

import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.changename.domain.api.ChangeNameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetUserNameUseCase @Inject constructor(
    private val repository: ChangeNameRepository
) {
    fun execute(userName: String): Flow<Result<Unit, ErrorType>> {
        return repository.setUserName(userName)
    }
}