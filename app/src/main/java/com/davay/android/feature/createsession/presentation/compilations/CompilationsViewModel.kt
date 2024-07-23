package com.davay.android.feature.createsession.presentation.compilations

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.domain.models.CompilationFilms
import com.davay.android.domain.models.ErrorScreenState
import com.davay.android.domain.models.ErrorType
import com.davay.android.feature.createsession.domain.api.GetCollectionsUseCase
import com.davay.android.feature.createsession.domain.model.Compilation
import com.davay.android.utils.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompilationsViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow<CompilationsState>(CompilationsState.Loading)
    val state = _state.asStateFlow()

    private val selectedCompilations = mutableListOf<Compilation>()

    init {
        getCollectionList()
    }

    fun getCollectionList() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getCollectionsUseCase.execute()
            }.onSuccess { result ->
                handleResult(result)
            }.onFailure { error ->
                Log.v(TAG, "error -> ${error.localizedMessage}")
                error.printStackTrace()
                _state.value = CompilationsState.Error(ErrorScreenState.SERVER_ERROR)
            }
        }
    }

    private fun handleResult(result: Resource<List<CompilationFilms>>) {
        when (result) {
            is Resource.Success -> handleSuccess(result)
            is Resource.Error -> handleError(result.errorType)
        }
    }

    private fun handleSuccess(result: Resource.Success<List<CompilationFilms>>) {
        if (result.data.isEmpty()) {
            _state.value = CompilationsState.Error(ErrorScreenState.EMPTY)
        } else {
            val compilations = result.data.map {
                it.toCompilation()
            }
            _state.value = CompilationsState.Content(compilations)
        }
    }

    private fun handleError(errorType: ErrorType) {
        _state.value = renderError(errorType)
    }


    private fun renderError(errorType: ErrorType): CompilationsState.Error {
        return when (errorType) {
            ErrorType.NO_CONNECTION -> CompilationsState.Error(ErrorScreenState.NO_INTERNET)
            ErrorType.NOT_FOUND -> CompilationsState.Error(ErrorScreenState.SERVER_ERROR)
            ErrorType.BAD_REQUEST -> CompilationsState.Error(ErrorScreenState.SERVER_ERROR)
            ErrorType.APP_VERSION_ERROR -> CompilationsState.Error(ErrorScreenState.APP_VERSION_ERROR)
            else -> CompilationsState.Error(ErrorScreenState.SERVER_ERROR)
        }
    }

    fun compilationClicked(compilation: Compilation) {
        if (compilation.isSelected) {
            selectedCompilations.add(compilation)
        } else {
            selectedCompilations.remove(compilation)
        }
    }

    fun buttonContinueClicked() {
        Log.d("MyTag", selectedCompilations.toString())
    }

    fun CompilationFilms.toCompilation() = Compilation(
        id = this.id,
        name = this.name,
        cover = this.imgUrl,
        isSelected = false
    )

    private companion object {
        val TAG = CompilationsViewModel::class.simpleName
    }
}
