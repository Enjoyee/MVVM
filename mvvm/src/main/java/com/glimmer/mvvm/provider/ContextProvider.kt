package com.glimmer.mvvm.provider

import android.app.Application
import android.content.Context
import com.glimmer.mvvm.Hammer

object ContextProvider {
    internal lateinit var mApplication: Application

    fun attachContext(context: Context?) {
        mApplication = context as? Application ?: throw RuntimeException("init MVVM error!")
        Hammer.init(mApplication)
    }
}