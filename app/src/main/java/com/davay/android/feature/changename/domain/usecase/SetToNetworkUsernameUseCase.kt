package com.davay.android.feature.changename.domain.usecase

import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.User
import com.davay.android.feature.changename.domain.api.ChangeNameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetToNetworkUsernameUseCase @Inject constructor(
    private val registrationRepository: ChangeNameRepository
) {
    fun setUserName(userData: User): Flow<Result<UserDto, ErrorType>> {
        return registrationRepository.setUserName(userData)
    }
}