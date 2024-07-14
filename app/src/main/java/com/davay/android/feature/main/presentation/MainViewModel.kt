package com.davay.android.feature.main.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.domain.usecases.GetSingleDataUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserName: GetSingleDataUseCase<String>
) : BaseViewModel() {
    fun getNameOfUser(): String {
        return getUserName.getSingleData()
    }
}