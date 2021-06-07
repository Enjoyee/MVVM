package com.glimmer.mvvm.view

import androidx.lifecycle.ViewModel
import com.glimmer.mvvm.lifecycle.ApplicationLifecycle

interface IApplication {
    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T? {
        return ApplicationLifecycle.mApplicationDelegateImpl?.getViewModel(modelClass)
    }

    fun <T : ViewModel> getViewModel(key: String, modelClass: Class<T>): T? {
        return ApplicationLifecycle.mApplicationDelegateImpl?.getViewModel(key, modelClass)
    }
}