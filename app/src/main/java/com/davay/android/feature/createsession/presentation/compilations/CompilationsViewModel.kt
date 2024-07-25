package com.davay.android.feature.createsession.presentation.compilations

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.commit451.translationviewdraghelper.BuildConfig
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.models.CompilationFilms
import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.createsession.domain.model.CompilationSelect
import com.davay.android.feature.createsession.domain.usecase.GetCollectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompilationsViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow<CompilationsState>(CompilationsState.Loading)
    val state = _state.asStateFlow()

    private val selectedCompilations = mutableListOf<CompilationSelect>()

    init {
        getCollectionList()
    }

    fun getCollectionList() {
        _state.update { CompilationsState.Loading }
        runSafelyUseCase(
            useCaseFlow = getCollectionsUseCase.execute(),
            onSuccess = { collections ->
                if (collections.isEmpty()) {
                    _state.update { CompilationsState.Error(ErrorScreenState.EMPTY) }
                } else {
                    val compilations = collections.map { it.toCompilation() }
                    _state.update { CompilationsState.Content(compilations) }
                }
            },
            onFailure = { error ->
                _state.update { CompilationsState.Error(renderError(error)) }
            }
        )
    }

    private fun renderError(errorType: ErrorType): ErrorScreenState {
        return when (errorType) {
            ErrorType.NO_CONNECTION -> ErrorScreenState.NO_INTERNET
            ErrorType.NOT_FOUND -> ErrorScreenState.SERVER_ERROR
            ErrorType.BAD_REQUEST -> ErrorScreenState.SERVER_ERROR
            ErrorType.APP_VERSION_ERROR -> ErrorScreenState.APP_VERSION_ERROR
            else -> ErrorScreenState.SERVER_ERROR
        }
    }

    fun compilationClicked(compilation: CompilationSelect) {
        if (compilation.isSelected) {
            selectedCompilations.add(compilation)
        } else {
            selectedCompilations.remove(compilation)
        }
    }

    fun buttonContinueClicked() {
        Log.d("MyTag", selectedCompilations.toString())
    }

    private inline fun <reified D> runSafelyUseCase(
        useCaseFlow: Flow<Result<D, ErrorType>>,
        noinline onFailure: ((ErrorType) -> Unit)? = null,
        crossinline onSuccess: (D) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                useCaseFlow.collect { result ->
                    when (result) {
                        is Result.Success -> onSuccess(result.data)
                        is Result.Error -> {
                            onFailure?.invoke(result.error)
                                ?: _state.update { CompilationsState.Error(renderError(result.error)) }
                        }
                    }
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    Log.v(TAG, "error -> ${error.localizedMessage}")
                    error.printStackTrace()
                }
                onFailure?.invoke(ErrorType.UNKNOWN_ERROR)
                    ?: _state.update { CompilationsState.Error(renderError(ErrorType.UNKNOWN_ERROR)) }
            }
        }
    }

    fun CompilationFilms.toCompilation() = CompilationSelect(
        id = this.id,
        name = this.name,
        cover = this.imgUrl ?: "",
        isSelected = false
    )

    private companion object {
        val TAG = CompilationsViewModel::class.simpleName
    }
}