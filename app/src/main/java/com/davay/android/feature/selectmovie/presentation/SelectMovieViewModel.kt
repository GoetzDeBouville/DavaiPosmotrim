package com.davay.android.feature.selectmovie.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.feature.selectmovie.domain.SaveSessionsHistoryRepository
import javax.inject.Inject

class SelectMovieViewModel @Inject constructor(
    private val saveSessionsHistoryRepository: SaveSessionsHistoryRepository
) : BaseViewModel() {

}