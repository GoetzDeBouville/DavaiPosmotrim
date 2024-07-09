package com.davay.android.feature.main.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.userdata.UserDataInteractor
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userDataInteractor: UserDataInteractor
) : BaseViewModel() {
    fun getUserName(): String {
        return userDataInteractor.getUserName()
    }
}