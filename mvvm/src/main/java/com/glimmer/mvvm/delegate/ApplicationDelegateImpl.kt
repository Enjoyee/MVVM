package com.glimmer.mvvm.delegate

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.glimmer.mvvm.Hammer
import com.glimmer.mvvm.view.IApplication
import com.tencent.mmkv.MMKV
import timber.log.Timber

internal class ApplicationDelegateImpl(private val application: Application) : IApplication, ApplicationDelegate {
    private val mViewModelStore by lazy { ViewModelStore() }
    private val mViewModelProviderFactory by lazy { ViewModelProvider.AndroidViewModelFactory(application) }

    init {
        MMKV.initialize(application)
        if (Timber.forest().isEmpty()) {
            if (Hammer.mConfig.mShowLog.invoke()) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(object : Timber.Tree() {
                    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                        if (priority >= Log.ERROR) {
                            Timber.tag(tag).e(t)
                        }
                    }
                })
            }
        }
    }

    override fun getViewModelStore(): ViewModelStore = mViewModelStore

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory = mViewModelProviderFactory

    override fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass)
    }

    override fun <T : ViewModel> getViewModel(key: String, modelClass: Class<T>): T = ViewModelProvider(this).get(key, modelClass)

}