package com.glimmer.mvvm.lifecycle

import android.app.Application
import com.glimmer.mvvm.delegate.ApplicationDelegateImpl

object ApplicationLifecycle {
    internal var mApplicationDelegateImpl: ApplicationDelegateImpl? = null

    fun register(application: Application) {
        mApplicationDelegateImpl = ApplicationDelegateImpl(application)
    }

}