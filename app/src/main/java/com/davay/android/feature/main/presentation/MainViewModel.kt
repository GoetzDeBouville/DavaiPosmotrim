package com.davay.android.feature.main.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.base.usecases.GetSharedPreferences
import com.davay.android.utils.SharedKeys
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserData: GetSharedPreferences<String>
) : BaseViewModel() {
    fun getUserName(): String {
        return getUserData.getSharedPreferences(SharedKeys.USER_NAME)
    }
}