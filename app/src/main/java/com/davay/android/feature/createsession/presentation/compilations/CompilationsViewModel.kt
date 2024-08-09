package com.davay.android.feature.createsession.presentation.compilations

import android.util.Log
import com.davay.android.BuildConfig
import com.davay.android.core.domain.models.CompilationFilms
import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.feature.createsession.domain.model.CompilationSelect
import com.davay.android.feature.createsession.domain.usecase.CreateSessionUseCase
import com.davay.android.feature.createsession.domain.usecase.GetCollectionsUseCase
import com.davay.android.feature.createsession.presentation.createsession.CreateSessionViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class CompilationsViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val createSessionUseCase: CreateSessionUseCase
) : CreateSessionViewModel() {
    private val _state = MutableStateFlow<CompilationsState>(CompilationsState.Loading)
    val state = _state.asStateFlow()

    private val selectedCompilations = mutableListOf<CompilationSelect>()

    init {
        getCollectionList()
    }

    fun getCollectionList() {
        runSafelyUseCase(
            useCaseFlow = getCollectionsUseCase.execute(),
            onSuccess = { collections ->
                if (collections.isEmpty()) {
                    _state.update { CompilationsState.Error(ErrorScreenState.EMPTY) }
                } else {
                    val compilations = collections.map { it.toUiModel() }
                    _state.update { CompilationsState.Content(compilations) }
                }
            },
            onFailure = { error ->
                _state.update { CompilationsState.Error(mapErrorToUiState(error)) }
            }
        )
    }

    fun compilationClicked(compilation: CompilationSelect) {
        if (compilation.isSelected) {
            selectedCompilations.add(compilation)
        } else {
            selectedCompilations.remove(compilation)
        }
    }

    private fun CompilationFilms.toUiModel() = CompilationSelect(
        id = this.id,
        name = this.name,
        cover = this.imgUrl ?: "",
        isSelected = false
    )

    fun resetSelections() {
        selectedCompilations.clear()
        _state.update { currentState ->
            if (currentState is CompilationsState.Content) {
                CompilationsState.Content(currentState.compilationList.map { it.copy(isSelected = false) })
            } else {
                currentState
            }
        }
    }

    /**
     * Метод проверяет на пустоту список выбранных коллекций, если список пустой, вызывает баннер.
     * Для не пустого списка вызывает создание сессии.
     * Навигация при этом вызывается только после успешного возврата.
     * Данные сессии передаем через bundle в navigateToWaitSession.
     */
    fun createSessionAndNavigateToWaitSessionScreen(showBanner: () -> Unit) {
        if (selectedCompilations.isEmpty()) {
            showBanner.invoke()
        } else {
            val collections = selectedCompilations.map {
                it.id
            }
            _state.update {
                CompilationsState.CreateSessionLoading
            }
            runSafelyUseCase(
                useCaseFlow = createSessionUseCase.execute(PARAMETER_NAME, collections),
                onSuccess = { session ->
                    if (BuildConfig.DEBUG) {
                        Log.v(TAG, "session = $session")
                    }
                    _state.update { CompilationsState.SessionCreated(session) }
                    navigateToWaitSession(session)
                },
                onFailure = { error ->
                    if (BuildConfig.DEBUG) {
                        Log.v(TAG, "error -> $error")
                    }
                    _state.update { CompilationsState.Error(mapErrorToUiState(error)) }
                }
            )
        }
    }

    private companion object {
        const val PARAMETER_NAME = "collections"
        val TAG = CompilationsViewModel::class.simpleName
    }
}