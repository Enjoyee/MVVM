package com.glimmer.mvvm.delegate

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.glimmer.mvvm.view.IApplication
import com.tencent.mmkv.MMKV

internal class ApplicationDelegateImpl(private val application: Application) : IApplication, ApplicationDelegate {
    private val mViewModelStore by lazy { ViewModelStore() }
    private val mViewModelProviderFactory by lazy { ViewModelProvider.AndroidViewModelFactory(application) }

    init {
        MMKV.initialize(application)
    }

    override fun getViewModelStore(): ViewModelStore = mViewModelStore

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory = mViewModelProviderFactory

    override fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass)
    }

    override fun <T : ViewModel> getViewModel(key: String, modelClass: Class<T>): T = ViewModelProvider(this).get(key, modelClass)

}