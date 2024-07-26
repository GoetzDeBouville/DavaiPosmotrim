package com.davay.android.feature.main.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.domain.models.UserDataFields
import com.davay.android.domain.usecases.GetUserDataUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserData: GetUserDataUseCase
) : BaseViewModel() {
    fun getUserName(): String {
        return getUserData.getUserData(UserDataFields.UserName())
    }
}