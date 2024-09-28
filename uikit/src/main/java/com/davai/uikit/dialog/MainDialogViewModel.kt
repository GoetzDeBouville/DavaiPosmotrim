package com.davai.uikit.dialog

import androidx.lifecycle.ViewModel

class MainDialogViewModel : ViewModel() {
    var title: String? = null
    var message: String? = null
    var yesAction: (() -> Unit)? = null
    var noAction: (() -> Unit)? = null
    var onCancelAction: (() -> Unit)? = null
    var showConfirmBlock: Boolean = false
}