package com.davay.android.feature.sessionsmatched.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.domain.models.Session
import com.davay.android.domain.models.SessionStatus
import com.davay.android.domain.models.User
import com.davay.android.feature.sessionsmatched.domain.GetSessionsHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchedSessionsViewModel @Inject constructor(
    private val getSessionsHistoryRepository: GetSessionsHistoryRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow<MatchedSessionsState>(MatchedSessionsState.Loading)
    val state = _state.asStateFlow()

    init {
        getMatchedSessions()
    }

    private fun getMatchedSessions() {
        viewModelScope.launch {
            _state.value = MatchedSessionsState.Loading
            val list = getSessionsHistoryRepository.getSessionsHistory()
            if (list.isNullOrEmpty()) {
                _state.value = MatchedSessionsState.Empty
            } else {
                _state.value =
                    MatchedSessionsState.Content(getSessionsHistoryRepository.getSessionsHistory())
            }
        }
    }
}

@Suppress("Detekt.MagicNumber", "Detekt.StringLiteralDuplication", "Detekt.UnderscoresInNumericLiterals")
private val matchedSessions = listOf(
    Session(
        id = "11Sq21",
        users = listOf(
            User(userId = "dqwdqw123dad", name = "Riley Bryan"),
            User(userId = "mandamus", name = "Adolfo Burks"),
            User(userId = "molestie", name = "Kate Griffin"),
            User(userId = "ponderum", name = "Earl England"),
            User(userId = "senserit", name = "Maribel Daniel")
        ),
        numberOfMatchedMovies = 1,
        date = 1707350400000L,
        status = SessionStatus.VOTING,
        imgUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1704946/0a54b73c-bbec-4ec0-9516-0faaf7ebe9fa/3840x"
    ),
    Session(
        id = "223q21",
        users = listOf(
            User(userId = "dqwdqw123dad", name = "Riley Bryan"),
            User(userId = "convallis", name = "Liza Cotton"),
            User(userId = "mandamus", name = "Adolfo Burks"),
            User(userId = "consectetuer", name = "Lavonne Beasley")
        ),
        numberOfMatchedMovies = 6,
        date = 1716249600000L,
        status = SessionStatus.CLOSED,
        imgUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/4303601/119d3aad-a479-4532-aadf-b428eb330337/3840x"
    ),
    Session(
        id = "curae",
        users = listOf(
            User(userId = "dqwdqw123dad", name = "Riley Bryan"),
            User(userId = "mandamus", name = "Adolfo Burks"),
            User(userId = "senserit", name = "Maribel Daniel"),
            User(userId = "convallis", name = "Liza Cotton")
        ),
        numberOfMatchedMovies = 4,
        date = 1718150400000L,
        status = SessionStatus.WAITING,
        imgUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/6e9e2641-5750-4991-a52b-8c1cd0e80b4c/3840x"
    ),
    Session(
        id = "risus",
        users = listOf(
            User(userId = "dqwdqw123dad", name = "Riley Bryan"),
            User(userId = "contentiones", name = "Forest Summers"),
            User(userId = "molestiae", name = "Sherri Saunders"),
            User(userId = "molestie", name = "Kate Griffin"),
            User(userId = "ponderum", name = "Earl England"),
            User(userId = "senserit", name = "Maribel Daniel")
        ),
        numberOfMatchedMovies = 3,
        date = 1719964800000L,
        status = SessionStatus.CLOSED,
        imgUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/4774061/0d60959d-fef2-4f1f-a28b-3d08247ec817/3840x"
    ),
    Session(
        id = "i1243hr36w",
        users = listOf(
            User(userId = "dqwdqw123dad", name = "Riley Bryan"),
            User(userId = "contentiones", name = "Forest Summers"),
            User(userId = "molestiae", name = "Sherri Saunders"),
            User(userId = "molestie", name = "Kate Griffin"),
            User(userId = "ponderum", name = "Earl England"),
            User(userId = "senserit", name = "Maribel Daniel")
        ),
        numberOfMatchedMovies = 3,
        date = 1718236800000L,
        status = SessionStatus.CLOSED,
        imgUrl = ""
    )
)