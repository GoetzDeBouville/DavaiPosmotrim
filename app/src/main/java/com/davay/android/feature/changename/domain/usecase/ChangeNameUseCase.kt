package com.davay.android.feature.changename.domain.usecase

import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.changename.domain.api.ChangeNameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeNameUseCase @Inject constructor(
    private val registrationRepository: ChangeNameRepository
) {
    fun setUserName(userName: String): Flow<Result<UserDto, ErrorType>> {
        return registrationRepository.setUserName(userName)
    }
}