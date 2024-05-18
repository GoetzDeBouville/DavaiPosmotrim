package com.davay.android.feature.createsession.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.feature.createsession.domain.model.Compilation
import javax.inject.Inject

class CompilationsViewModel @Inject constructor() : BaseViewModel() {

    private val selectedCompilations = mutableListOf<Compilation>()

    fun compilationClicked(compilation: Compilation) {
        if (compilation.isSelected) {
            selectedCompilations.add(compilation)
        } else {
            selectedCompilations.remove(compilation)
        }
    }
}
