package com.davai.uikit

import androidx.lifecycle.ViewModel

class MainDialogViewModel : ViewModel() {
    var title: String? = null
    var message: String? = null
    var yesAction: (() -> Unit)? = null
    var noAction: (() -> Unit)? = null
}