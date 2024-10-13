package com.davay.android.feature.moviecard.presentation

import androidx.core.os.bundleOf
import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.feature.matchedsession.presentation.MatchedSessionFragment.Companion.SESSION_ID
import javax.inject.Inject

class MovieCardViewModel @Inject constructor(
    private val commonWebsocketInteractor: CommonWebsocketInteractor
) : BaseViewModel() {

    init {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.getSessionStatus(),
            onSuccess = { sessionStatus ->
                if (sessionStatus == SessionStatus.ROULETTE) {
                    navigate(R.id.action_movieCardFragment_to_rouletteFragment)
                } else if (sessionStatus == SessionStatus.CLOSED) {
                    navigate(
                        R.id.action_movieCardFragment_to_matchedSessionFragment,
                        bundleOf(SESSION_ID to commonWebsocketInteractor.sessionId)
                    )
                }
            },
            onFailure = { }
        )
    }
}